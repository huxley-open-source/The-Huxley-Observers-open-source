package com.thehuxley.observer.plag.fileFinder;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: romero
 * Date: 13/05/12
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public interface FinderAlgorithm {
    public static final String PROBLEM_ID = "PROBLEM_ID";
    public static final String INSTITUTION_ID = "INSTITUTION_ID";
    public static final String LANGUAGE_NAME = "LANGUAGE_NAME";
    public static final String SUBMISSION_DIR = "SUBMISSION_DIR";
    public static final String ALGORITHM_CLUSTER = "CLUSTER";
    public static final String ALGORITHM_DEFAULT = "DEFAULT";

    public void initialize(HashMap<String,Object> params);
    public java.util.List<java.io.File> getFiles();
    public void cleanEntries();
}
