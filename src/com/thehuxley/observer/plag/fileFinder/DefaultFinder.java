package com.thehuxley.observer.plag.fileFinder;

import com.thehuxley.observer.data.model.Language;
import com.thehuxley.observer.languageManager.LanguageManager;
import net.sourceforge.pmd.util.FileFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: romero
 * Date: 26/09/13
 * Time: 08:40
 * To change this template use File | Settings | File Templates.
 */
public class DefaultFinder implements FinderAlgorithm{
    int problemId;
    String languageName;
    int institutionId;
    public java.util.List<java.io.File> fileList;
    public String submissionDir;
    public String similarityFactor;
    public int yearFactor;
    private static final Logger log = LoggerFactory.getLogger(DefaultFinder.class);

    public void initialize(HashMap<String, Object> params) {
        cleanEntries();
        problemId = (Integer) params.get(PROBLEM_ID);
        languageName = (String) params.get(LANGUAGE_NAME);
        submissionDir = (String) params.get(SUBMISSION_DIR);
    }

    public List<File> getFiles() {
        Language language = LanguageManager.getInstance().getByName(languageName);
        log.info("Gerando lista de arquivos");
        File problemDir = new File(submissionDir,Integer.toString(problemId));
        if (!problemDir.exists()) {
            log.debug("A pasta de submissões não foi encontrada. A procura foi realizada em:"
                    + problemDir.getAbsolutePath());
            return null;
        }

        String[] users = problemDir.list();
        for (int i = 0; i < users.length; i++) {
            String userDir = users[i];
            File fUserDir = new File(problemDir, userDir);
            if (fUserDir.isDirectory()){
                String[] userLanguages = fUserDir.list();
                for (int j = 0; j < userLanguages.length; j++) {
                    String userLanguage = userLanguages[j];
                    if (userLanguage.equals(language.getName())) {
                        File fUserLanguage = new File(fUserDir, userLanguage);
                        String[] tries = fUserLanguage.list();
                        for(int k = 0; k < tries.length; k++){
                            String trieDir = tries[k];
                            File fTrieDir = new File(fUserLanguage, trieDir);
                            if(fTrieDir.isDirectory()){
                                String[] submissionFile = fTrieDir.list();
                                for(int o = 0 ; o < submissionFile.length; o++){
                                    if(submissionFile[o].contains(language.getExtension())){
                                        String submission = submissionFile[o];
                                        File submissionSourceFile = new File(fTrieDir, submission);
                                        fileList.add(submissionSourceFile);
                                    }
                                }


                            }
                        }

                    }
                }
            }
        }
        return fileList;
    }

    public void cleanEntries() {
        log.info("Limpando as Entradas");
        fileList = new ArrayList<File>();
    }
}
