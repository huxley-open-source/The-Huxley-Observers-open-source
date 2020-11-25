package com.thehuxley.observer;

import com.thehuxley.observer.plag.HuxleyFacade;
import com.thehuxley.observer.plag.plagAlgorithm.sherlock.Sherlock;

/**
 * Created by IntelliJ IDEA.
 * User: romero
 * Date: 13/05/12
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class testePlagium {

    public static void main(String[] args) throws Exception {
            HuxleyFacade.getInstance().verifyPlagiarizedSubmissions();
//        new Sherlock("bla").verifyPlagiarizedSubmissions(null);

        }



}
