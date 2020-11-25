package com.thehuxley.observer.plag;

import com.thehuxley.observer.data.Configurator;
import com.thehuxley.observer.plag.fileFinder.ClusterFinder;
import com.thehuxley.observer.plag.fileFinder.DefaultFinder;
import com.thehuxley.observer.plag.fileFinder.FinderAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: romero
 * Date: 13/05/12
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class FileFinder {
    public static FileFinder instance;
    public FinderAlgorithm finder;
    public String submissionDir;
    private static final Logger log = LoggerFactory.getLogger(FileFinder.class);

    private FileFinder(){
        String algorithm = Configurator.getProperty("plag.dir.algorithm");
        submissionDir = Configurator.getProperty("submission.dir");
        setAlgorithm(algorithm);
    }
    public static FileFinder getInstance(){
        if(instance == null){
            instance = new FileFinder();
        }
        return  instance;
    }

    public void setAlgorithm(String algorithm){
        log.info("Inicializando o tipo do algoritmo para: " + algorithm);
        if(algorithm.equals(FinderAlgorithm.ALGORITHM_CLUSTER)){
            finder = new ClusterFinder();
        }
        if(algorithm.equals(FinderAlgorithm.ALGORITHM_DEFAULT)){
            finder = new DefaultFinder();
        }

    }

    public void initialize(HashMap<String,Object> params){
        params.put(FinderAlgorithm.SUBMISSION_DIR,submissionDir);
        finder.initialize(params);
    }
    public void clean(){
        finder.cleanEntries();
    }
    public java.util.List<java.io.File> getFiles(){
        return finder.getFiles();
    }
    public void setSubmissionDir(String submissionDir){
        this.submissionDir = submissionDir;
    }
}
