package com.thehuxley.observer.plag.fileFinder;

import com.thehuxley.observer.data.Configurator;
import com.thehuxley.observer.data.DBAdapter;
import com.thehuxley.observer.data.model.Language;
import com.thehuxley.observer.languageManager.LanguageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: romero
 * Date: 13/05/12
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class ClusterFinder implements FinderAlgorithm{

    int problemId;
    String languageName;
    int institutionId;
    public java.util.List<java.io.File> fileList;
    public String submissionDir;
    public String similarityFactor;
    public int yearFactor;
    private static final Logger log = LoggerFactory.getLogger(ClusterFinder.class);

    public void initialize(HashMap<String,Object> params){
        cleanEntries();
        problemId = (Integer) params.get(PROBLEM_ID);
        institutionId = (Integer) params.get(INSTITUTION_ID);
        languageName = (String) params.get(LANGUAGE_NAME);
        submissionDir = (String) params.get(SUBMISSION_DIR);
        yearFactor = Integer.parseInt(Configurator.getProperty("plag.year.limit"));
        similarityFactor = Configurator.getProperty("similarity.factor");
        log.info("Inicializando o algoritmo com os valores: \nproblemId = " + problemId + "institutionId = " + institutionId + "\nlanguageName = " + languageName + " submissionDir = " + submissionDir);
    }
    public void cleanEntries(){
        log.info("Limpando as Entradas");
        fileList = new ArrayList<File> ();
    }

    public java.util.List<java.io.File> getFiles(){
        log.info("Gerando lista de arquivos");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.YEAR,-yearFactor);
        try {
            Language language = LanguageManager.getInstance().getByName(languageName);
            if(language != null){
                for(String path : DBAdapter.getInstance().getSubmissionDao().getSubmissionPathByCluster(language.getId()+ "",problemId + "",institutionId + "",calendar.get(Calendar.YEAR) + "",similarityFactor)){
                    File test = new File(submissionDir + path.replace("language",languageName));
                    fileList.add(test);
                }

            }else{
                log.error("Linguagem não encontrada: " + languageName);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fileList;


    }
}
