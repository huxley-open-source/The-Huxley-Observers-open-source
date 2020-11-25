package com.thehuxley.observer.data;

import com.mysql.jdbc.MySQLConnection;
import com.thehuxley.observer.data.dao.LanguageDao;
import com.thehuxley.observer.data.dao.SubmissionDao;
import com.thehuxley.observer.data.dao.mysql.LanguageDaoMySQL;
import com.thehuxley.observer.data.dao.mysql.SubmissionDaoMySQL;

/**
 * Created by IntelliJ IDEA.
 * User: romero
 * Date: 13/05/12
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
public class DBAdapter {
    public static DBAdapter instance;
    public String sgdbName;
    public static final String SGBD_MYSQL = "MYSQL";
    private DBAdapter(){
        sgdbName = Configurator.getProperty("sgbd");
    }
    public static DBAdapter getInstance(){
        if(instance == null){
            instance = new DBAdapter();
        }
        return instance;
    }

    public SubmissionDao getSubmissionDao(){
        if(sgdbName.equals(SGBD_MYSQL)){
            return new SubmissionDaoMySQL();
        }
        return null;
    }

     public LanguageDao getLanguageDao(){
        if(sgdbName.equals(SGBD_MYSQL)){
            return new LanguageDaoMySQL();
        }
        return null;
    }
}
