package com.thehuxley.observer.achievement;

import com.thehuxley.event.Event;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: romero
 * Date: 25/01/12
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class AchievementManager {
    private Hashtable<Integer, ArrayList<Achievement>> achievementTable;

    private AchievementManager(){
        achievementTable = new Hashtable<Integer, ArrayList<Achievement>>();
    }

    public void verify(Event event){

        ArrayList<Achievement> toVerify = achievementTable.get(event.type);

        for(Achievement achievement: toVerify){
            achievement.verify(event);

        }

    }
}
