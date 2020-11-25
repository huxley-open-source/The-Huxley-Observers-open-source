package com.thehuxley.observer.plag.plagAlgorithm;

import com.thehuxley.observer.data.Configurator;
import com.thehuxley.observer.data.dao.CPDQueueDao;
import com.thehuxley.observer.data.dao.FragmentDao;
import com.thehuxley.observer.data.dao.PlagiumDao;
import com.thehuxley.observer.data.dao.SubmissionDao;
import com.thehuxley.observer.data.dao.mysql.CPDQueueDaoMySQL;
import com.thehuxley.observer.data.dao.mysql.FragmentDaoMySQL;
import com.thehuxley.observer.data.dao.mysql.PlagiumDaoMySQL;
import com.thehuxley.observer.data.dao.mysql.SubmissionDaoMySQL;
import com.thehuxley.observer.data.model.CPDQueue;
import com.thehuxley.observer.data.model.Fragment;
import com.thehuxley.observer.data.model.Plagium;
import com.thehuxley.observer.data.model.Submission;
import com.thehuxley.observer.languageManager.LanguageManager;
import com.thehuxley.observer.plag.FileFinder;
import com.thehuxley.observer.plag.fileFinder.FinderAlgorithm;
import com.thehuxley.observer.plag.plagAlgorithm.cPD.CPDResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: romero
 * Date: 26/09/13
 * Time: 09:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class PlagAlgorithm {
    public static final String ALGORITHM_CPD = "CPD";
    public static final String ALGORITHM_SHERLOCK = "SHERLOCK";
    protected final String SUBMISSION_DIR;
    private static final Logger log = LoggerFactory.getLogger(PlagAlgorithm.class);
    protected final double SIMILARITY_THRESHOLD;

    public PlagAlgorithm(String submissionDir){
        SUBMISSION_DIR = submissionDir;
        FileFinder.getInstance().setSubmissionDir(submissionDir);
        SIMILARITY_THRESHOLD =  Double.parseDouble(Configurator.getProperty("similarity.threshold"));
    }

    /**
     * O cPD retorna todos os plágios, inclusive ele considera plágios de um mesmo usuário.
     * Logo, é preciso eliminar os plágios que forem entre o mesmo usuário.
     *
     * O Retorno é um map. Cada entrada do map contém uma lista de CPDresult. Essa lista já está agrupada por
     * plágios entre arquivos de diferentes usuários. Note que 02 arquivos podem conter várias seções de plágio.
     *
     *
     * @return
     */
    protected Map<String, List<CPDResult>> filter(List<CPDResult> allResults) {
        int allResultsSize = allResults.size();
        Map<String, List<CPDResult>> plagMap = new HashMap<String, List<CPDResult>>();
        log.debug("filter - Iterando allResults de CPDResult.");
        for (CPDResult cpdResult : allResults) {
            //Ver a documentação do cpdResult. 2, significa mesmo usuário.
            if (!cpdResult.hasSameRoot(CPDResult.ROOT_LEVEL_USER)){
                String ordered = cpdResult.getFileName1()+"&"+cpdResult.getFileName2();
                String invOrdered = cpdResult.getFileName2()+"&"+cpdResult.getFileName1();


                List <CPDResult> fragments = null;
                fragments = plagMap.get (ordered);

                if (fragments == null){
                    fragments = plagMap.get (invOrdered);
                }

                if (fragments==null){
                    // primeira vez
                    ArrayList<CPDResult> arrayList = new ArrayList<CPDResult>();
                    arrayList.add (cpdResult);
                    plagMap.put(ordered,arrayList);
                }else{
                    // já veio da hash, então não precisa adicionar de novo na hash (objetos são passados por referência)
                    fragments.add(cpdResult);
                }
            }
        }

        return plagMap;
    }


    public void verify(){
        CPDQueueDao cpdQueueDao = new CPDQueueDaoMySQL();
        for(CPDQueue element : cpdQueueDao.listCPDQueue()) {
                log.info("Verificando plágios do problema: "+element.getProblemId());
            try{
                String finder = Configurator.getProperty("finder");
                deleteOldPlagiums(LanguageManager.getInstance().getByName(element.getLanguage()).getId(), element.getProblemId());
                if("YES".equals(finder)){
                    HashMap<String,Object> params = new HashMap<String, Object>();
                    params.put(FinderAlgorithm.INSTITUTION_ID,element.getInstitutionId());
                    params.put(FinderAlgorithm.PROBLEM_ID,element.getProblemId());
                    params.put(FinderAlgorithm.LANGUAGE_NAME,element.getLanguage());
                    com.thehuxley.observer.plag.FileFinder.getInstance().initialize(params);
                    verifyPlagiarizedSubmissions (com.thehuxley.observer.plag.FileFinder.getInstance().getFiles());
                } else {
                    verifyPlagiarizedSubmissions (element.getProblemId() + "", element.getLanguage().toLowerCase());
                }
            }catch(Exception e){
                    e.printStackTrace();
                    log.error("Erro ao verificar o plagio do problema "+element.getProblemId()+", linguagem: "+element.getLanguage()+" .  "+e.getMessage(), e);
            }finally{
                try{
                    log.debug("Deletando CPDQueue.");
                    String algorithmDir = Configurator.getProperty("plag.dir.algorithm");
                    if(algorithmDir.equals("CLUSTER")){
                        cpdQueueDao.deleteCPDQueue(element.getId());
                    } else {
                        cpdQueueDao.deleteCPDQueueByLanguageAndProblem(element.getProblemId(), element.getLanguage());
                    }

                    }catch(Exception e){
                            log.error(e.getMessage(), e);
                    }
            }
            try{
                log.debug("Dormindo um pouco...");
            Thread.sleep(2000);
            }catch (Exception e ){
                log.error(e.getMessage(), e);
            }

        }
                //CPDQueue.deleteAll();
            SubmissionDao submission = new SubmissionDaoMySQL();
            submission.markAllAsNotMatched();
        }

    public void deleteOldPlagiums(long languageId, long problemId){
        PlagiumDao plagiumDao = new PlagiumDaoMySQL();
        plagiumDao.deletePlagiumByProblemAndLanguage(languageId, problemId);
    }
    // guarda os resultados no banco e marca cada submissão como suspeita, para aumentar a velocidade da view
    public void savePlagiums(Map<String, List<CPDResult>> plagMap){
        log.debug("Tentando salvar plágios...");
        SubmissionDao submissionDao = new SubmissionDaoMySQL();
        for(String key : plagMap.keySet()){
            List<CPDResult> cpdResults = plagMap.get (key);
            Plagium plagium = new Plagium();
            CPDResult first = cpdResults.get(0);
            try{
                Submission submission1 = submissionDao.getSubmissionFromUserIdProblemIdAttempts(first.getUserFromFile1(),first.getProblem(),first.getAttemptFromFile1());
                Submission submission2 = submissionDao.getSubmissionFromUserIdProblemIdAttempts(first.getUserFromFile2(),first.getProblem(),first.getAttemptFromFile2());
                // Só salva se forem ids diferentes.
                if (submission1.getId() != submission2.getId() && (submission1.getId() != 0  && submission2.getId() != 0)){

                    plagium.setSubmission1Id(submission1.getId());
                    plagium.setSubmission2Id(submission2.getId());
                    plagium.setPercentage (0);

                    double plagiumPercentage = 0;
                    double cpdResultPercentage;
                    for (CPDResult cpdResult : cpdResults) {
                        Fragment frag = new Fragment();
                        frag.setNumberOfLines (cpdResult.getLineCount());
                        cpdResultPercentage = cpdResult.getPercentage();
                        frag.setPercentage( cpdResultPercentage );
                        frag.setStartLine1( cpdResult.getFile1BeginLine() );
                        frag.setStartLine2( cpdResult.getFile2BeginLine() );

                        plagium.addToFragments(frag);
                        plagiumPercentage+=cpdResultPercentage;
                    }
                    // Só salva se ultrapassar o threshould de similaridade
                    if (plagiumPercentage >= SIMILARITY_THRESHOLD){
                        // alterar o status da submissão para indicar que elas estão sob suspeita de plágio.
//                        submission1.plagiumMatched();
//                        submission2.plagiumMatched();

                        plagium.setPercentage(plagiumPercentage);
                        PlagiumDao plagiumDao = new PlagiumDaoMySQL();
                        plagium.setId(plagiumDao.getLastId() + 1);
                        long plagiumId = plagiumDao.createPlagium(plagium);
                        if(plagiumId > 0){
                            FragmentDao fragmentDao = new FragmentDaoMySQL();
                            for(Fragment fragment:plagium.getFragmentList()){
                                fragment.setId(plagiumId);
                                fragmentDao.createFragment(fragment);
                            }
                                submissionDao.updatePlagiumStatus(submission1);
                                submissionDao.updatePlagiumStatus(submission2);
                        }else if(plagiumId == -1){
                            log.error("plagiumId = -1");
                        }
                    }
                }
            }catch(Exception e){
                log.error("Erro enquanto salvava plágios.", e);
            }
        }
    }

    public void savePlagium(CPDResult result){
        log.debug("Tentando salvar plágio...");
        SubmissionDao submissionDao = new SubmissionDaoMySQL();
            Plagium plagium = new Plagium();
            try{
                Submission submission1 = submissionDao.getSubmissionFromUserIdProblemIdAttempts(result.getUserFromFile1(),result.getProblem(),result.getAttemptFromFile1());
                Submission submission2 = submissionDao.getSubmissionFromUserIdProblemIdAttempts(result.getUserFromFile2(),result.getProblem(),result.getAttemptFromFile2());
                // Só salva se forem ids diferentes e usuarios diferentes
                if (submission1.getId() != submission2.getId() && (submission1.getId() > 0  && submission2.getId() > 0) && (submission1.getUserId() != submission2.getUserId())){

                    plagium.setSubmission1Id(submission1.getId());
                    plagium.setSubmission2Id(submission2.getId());
                    plagium.setPercentage (result.getPercentage());
                    // Só salva se ultrapassar o threshould de similaridade
                    if (plagium.getPercentage() >= SIMILARITY_THRESHOLD){
                        // alterar o status da submissão para indicar que elas estão sob suspeita de plágio.
//                        submission1.plagiumMatched();
//                        submission2.plagiumMatched();


                        PlagiumDao plagiumDao = new PlagiumDaoMySQL();
                        plagium.setId(plagiumDao.getLastId() + 1);
                        long plagiumId = plagiumDao.createPlagium(plagium);
                        if(plagiumId > 0){
                            submissionDao.updatePlagiumStatus(submission1);
                            submissionDao.updatePlagiumStatus(submission2);
                        }

                    }
                }
            }catch(Exception e){
                log.error("Erro enquanto salvava plágio.", e);
            }
    }

    public abstract void verifyPlagiarizedSubmissions(List<File> fileList);
    public abstract void verifyPlagiarizedSubmissions(String problem, String language);
}
