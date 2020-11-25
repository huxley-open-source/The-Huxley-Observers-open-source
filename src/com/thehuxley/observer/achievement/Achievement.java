package com.thehuxley.observer.achievement;

import com.thehuxley.event.Event;

/**
 * Created by IntelliJ IDEA.
 * User: romero
 * Date: 25/01/12
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class Achievement {
    private int id;

    public boolean verify(Event event) {
        if(!exists(event)){
            return executeTest(event);
        }
        return false;
    }

    public int getId(){
        return id;
    }
    //Testa se o usuário ja tem o achievement
    public boolean exists(Event event){
           return false;
    }
    //Verifica se o usuário esta apto ao achievement
    public boolean executeTest(Event event){
        return false;
    }


}
