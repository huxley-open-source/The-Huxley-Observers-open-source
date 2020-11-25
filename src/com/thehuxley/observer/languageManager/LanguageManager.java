package com.thehuxley.observer.languageManager;

import com.thehuxley.observer.data.DBAdapter;
import com.thehuxley.observer.data.model.Language;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: romero
 * Date: 13/05/12
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class LanguageManager {
    public static LanguageManager instance;
    public static HashMap<Long,Language> languageTable;
    private LanguageManager(){
        initialize();
    }
    public static LanguageManager getInstance(){
        if(instance==null){
            instance = new LanguageManager();
        }
        return instance;
    }

    public void initialize(){
        languageTable = new HashMap<Long, Language>();
        for(Language language : DBAdapter.getInstance().getLanguageDao().list()){
            languageTable.put(language.getId(),language);
        }
    }
    public Language getById(long id){
        return languageTable.get(id);
    }

    public Language getByName(String name){
        for(Language language : languageTable.values()){
            if(name.toLowerCase().equals(language.getName().toLowerCase())){
                return language;
            }
        }
        return null;
    }
}
