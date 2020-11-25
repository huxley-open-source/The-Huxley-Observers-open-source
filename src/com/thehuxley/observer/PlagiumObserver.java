package com.thehuxley.observer;

import com.thehuxley.event.AbstractObserver;
import com.thehuxley.event.Event;
import com.thehuxley.observer.data.Configurator;
import com.thehuxley.observer.plag.HuxleyFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlagiumObserver extends AbstractObserver {
	private static final String QUESTIONNAIRE_ID = "QUESTIONNAIRE_ID";
	private static Logger logger = LoggerFactory
			.getLogger(QuestionnaireCreateObserver.class);
	/**
	 * Carrega em uma variável estática para evitar ficar indo muitas vezes ao
	 * arquivo.
	 */
	private final static long loopInterval = Long.parseLong(Configurator
			.getProperty("loop_interval"));

	@Override
	protected String getServerHost() {
		return Configurator.getProperty("SERVER_HOST");
	}

	@Override
	protected int getServerPort() {
		return Integer.parseInt(Configurator.getProperty("SERVER_PORT"));
	}

	@Override
	protected String getObserverName() {
		return PlagiumObserver.class.getName();
	}

	@Override
	protected int getEventMask() {
		return Event.TYPE_START_PLAGIUM;
	}

	@Override
	protected long loopInterval() {
		return loopInterval;
	}

	@Override
	protected void performWork() {
		logger.info("performWork(), size: " + inBoxSize());
		logger.debug("Plagium realizando um trabalho...");
		try {
			/*
			 * tira do inbox e coloca em uma lista antes de processar, pois se
			 * ele processasse direto o inbox pode nunca ficar vazio, pois uma
			 * nova mensagem iria chegar
			 */
			ArrayList<HashMap<Integer, Object>> list = new ArrayList<HashMap<Integer, Object>>();
			while (inBoxSize() > 0) {
				list.add(poll());
			}
			/* Agora sim, faz o processamento */
			for (HashMap<Integer, Object> hashMap : list) {
				doThings(hashMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean filter(
			ConcurrentLinkedQueue<HashMap<Integer, Object>> inBox,
			HashMap<Integer, Object> newElement) {

        if(inBox.size() != 0){
				return true;
		}
		return false;
	}

	public void doThings(HashMap<Integer, Object> fromServer) {
		logger.debug("Plagium fazendo alguma coisa (doThing) :D");
        HuxleyFacade.getInstance().verifyPlagiarizedSubmissions();
	}

	public static void main(String[] args) throws Exception {
		PlagiumObserver observer = new PlagiumObserver();

	}

}
