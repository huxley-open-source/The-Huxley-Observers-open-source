package com.thehuxley.observer.plag.plagAlgorithm.cPD;

import com.thehuxley.observer.plag.plagAlgorithm.PlagAlgorithm;
import net.sourceforge.pmd.cpd.Language;
import net.sourceforge.pmd.cpd.LanguageFactory;
import net.sourceforge.pmd.cpd.Match;

import java.io.File;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rodrigo
 */
//ToDO Caso o algoritmo volte a ser utilizado deve ser adaptado a nova forma
public class CPDAdapter extends PlagAlgorithm {

    private final int MINIMUM_TOKENS = 25;
    private static final Logger log = LoggerFactory.getLogger(CPDAdapter.class);

    public CPDAdapter(String submissionDir){
        super(submissionDir);
    }



    /**
     * Retorna uma lista de plágios encontrados. Caso ocorra algum erro ou nenhum plágio seja encontrado, a lista é retornada vazia
     *
     * @return
     */
    public void verifyPlagiarizedSubmissions(List<File> fileList) {
    	log.debug("Iniciando a verificação de submissões plágiadas...");
        List<CPDResult> resultList = new ArrayList<CPDResult>(1000);

        try {
            LanguageFactory f = new LanguageFactory();
            Language language = f.createLanguage("cpp");

            MyCPD cpd = new MyCPD(MINIMUM_TOKENS, language);
            String encodingString = System.getProperty("file.encoding");

            cpd.setEncoding(encodingString);

            try{
                cpd.add(fileList);
            }catch(Exception e){
                log.error("Não foi possível verificar os plágios. Motivo:"+e.getMessage(), e);
            }
            resultList = executeVerify(cpd);
        } catch (NoSuchElementException e) {
        	log.error("No Such Element. :/", e);
        } catch (OutOfMemoryError ofMemoryError){
            log.error("Ficou sem memória! Ele esta morrendo! :(", ofMemoryError);
        }
    }

    @Override
    public void verifyPlagiarizedSubmissions(String problem, String language) {
    }

    private List<CPDResult> executeVerify(MyCPD cpd){

            List<CPDResult> resultList = new ArrayList<CPDResult>(1000);
            cpd.go();
            Iterator<Match> matches = cpd.getMatches();
            String arquivo1 = null;
            String arquivo2 = null;
            int file1BeginLine;
            int file2BeginLine;
            int lineCount;
            while (matches.hasNext()) {
                Match m = matches.next();
                // Sempre um mach é entre dois arquivos (mark)
                arquivo1 = m.getFirstMark().getTokenSrcID();
                file1BeginLine = m.getFirstMark().getBeginLine();


                arquivo2 = m.getSecondMark().getTokenSrcID();
                file2BeginLine = m.getSecondMark().getBeginLine();

                lineCount = m.getLineCount();

                if (!arquivo1.equals(arquivo2)) {

                    int numberOfTokens = cpd.getNumberOfTokens(arquivo1);
                    CPDResult result = new CPDResult(arquivo1, cpd.getNumberOfTokens(arquivo1), arquivo2, cpd.getNumberOfTokens(arquivo2), m.getTokenCount(), file1BeginLine, file2BeginLine,lineCount );
                    resultList.add(result);
                }
            }
     return resultList;
    }
}