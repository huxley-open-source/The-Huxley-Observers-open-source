package com.thehuxley.observer.plag;

import com.thehuxley.observer.plag.plagAlgorithm.cPD.CPDAdapter;
import com.thehuxley.observer.plag.plagAlgorithm.PlagAlgorithm;
import com.thehuxley.observer.plag.plagAlgorithm.sherlock.Sherlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.observer.data.Configurator;

public class HuxleyFacade {

	private static HuxleyFacade singleton;
	
	private static final Logger logger = LoggerFactory.getLogger(HuxleyFacade.class);

	private HuxleyFacade() {

	}

	public static HuxleyFacade getInstance() {
		if (singleton == null) {
			singleton = new HuxleyFacade();
		}
		return singleton;
	}

	public void verifyPlagiarizedSubmissions() {
		logger.debug("Facade, verifyPlagiarizedSubmissions...");
        String submissionDir = Configurator.getProperty("submission.dir");
        logger.debug("Diretorio de Submissoes: " + submissionDir);
        String algorithm = Configurator.getProperty("plag.dir.algorithm");
        FileFinder.getInstance().setAlgorithm(algorithm);
        String plagAlgorithmName = Configurator.getProperty("plag.algorithm");
        PlagAlgorithm plagAlgorithm;
        if(plagAlgorithmName.equals(PlagAlgorithm.ALGORITHM_SHERLOCK)){
            plagAlgorithm = new Sherlock(submissionDir);
        }else if(plagAlgorithmName.equals(PlagAlgorithm.ALGORITHM_CPD)){
            plagAlgorithm = new CPDAdapter(submissionDir);
        } else {
            plagAlgorithm = new CPDAdapter(submissionDir);
        }
        plagAlgorithm.verify();

	}
    public static void main(String[] args) {
         HuxleyFacade.getInstance().verifyPlagiarizedSubmissions();
     }
}
