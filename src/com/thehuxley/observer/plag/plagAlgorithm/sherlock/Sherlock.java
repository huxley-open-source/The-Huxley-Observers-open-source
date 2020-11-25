package com.thehuxley.observer.plag.plagAlgorithm.sherlock;

import com.thehuxley.observer.data.Configurator;
import com.thehuxley.observer.plag.plagAlgorithm.PlagAlgorithm;
import com.thehuxley.observer.plag.plagAlgorithm.cPD.CPDResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: romero
 * Date: 01/10/13
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */
public class Sherlock extends PlagAlgorithm  {

    public Sherlock(String submissionDir){
        super(submissionDir);
    }

    @Override
    public void verifyPlagiarizedSubmissions(List<File> fileList) {
        String dirList = "";
        for(File file: fileList){
            dirList += file.getAbsolutePath() + " ";
        }
        String cmd = Configurator.getProperty("sherlock.path") + " " + dirList;
        try{
            Process process = Runtime.getRuntime().exec(cmd);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            List<String> results = new ArrayList<String>();

            String line = null;
            while ((line = reader.readLine()) != null) {
                results.add(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Process exit code: " + exitCode); //Deve ser 0


            for (String result : results) {
                String[] parsedResult = result.split(";");
                CPDResult cpdResult = new CPDResult(parsedResult[0], parsedResult[1], Double.parseDouble(parsedResult[2].replace("%","")));
                savePlagium(cpdResult);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void verifyPlagiarizedSubmissions(String problemId, String languageExtension) {
        String dirList = "-z 3 -e " + languageExtension + " -t " + (int)(SIMILARITY_THRESHOLD * 100) + "% -r " + SUBMISSION_DIR  + problemId;

        String cmd = Configurator.getProperty("sherlock.path") + " " + dirList;
        try{
            Process process = Runtime.getRuntime().exec(cmd);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] parsedResult = line.split(";");
                CPDResult cpdResult = new CPDResult(parsedResult[0], parsedResult[1], Double.parseDouble(parsedResult[2].replace("%",""))/100);
                savePlagium(cpdResult);
            }
            int exitCode = process.waitFor();
            System.out.println("Process exit code: " + exitCode); //Deve ser 0

        } catch (Exception e){
            e.printStackTrace();

        }
    }
}
