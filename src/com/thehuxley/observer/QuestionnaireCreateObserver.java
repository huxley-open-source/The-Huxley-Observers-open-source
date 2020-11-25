package com.thehuxley.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.thehuxley.observer.data.dao.ObserverDao;
import com.thehuxley.observer.data.dao.mysql.ObseverDaoMySQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.thehuxley.event.AbstractObserver;
import com.thehuxley.event.AbstractObserver;
import com.thehuxley.event.Event;
import com.thehuxley.event.EventManagerProxySender;
import com.thehuxley.observer.data.Configurator;
import com.thehuxley.observer.data.dao.QuestionnaireDao;
import com.thehuxley.observer.data.dao.QuestionnaireShiroUserDao;
import com.thehuxley.observer.data.dao.mysql.QuestionnaireDaoMySQL;
import com.thehuxley.observer.data.dao.mysql.QuestionnaireShiroUserDaoMySQL;

public class QuestionnaireCreateObserver extends AbstractObserver {

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
		return QuestionnaireCreateObserver.class.getName();
	}

	@Override
	protected int getEventMask() {
		return Event.TYPE_QUESTIONNAIRE_SAVED;
	}

	@Override
	protected long loopInterval() {
		return loopInterval;
	}

	@Override
	protected void performWork() {
		logger.info("performWork(), size: " + inBoxSize());
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

		Event newEvent = (Event) newElement.get(Event.KEY_EVENT);
		Long newQuestId = Long.parseLong(newEvent.getParams().get(QUESTIONNAIRE_ID).toString());
        System.out.println("Evento: "+newEvent.creationTime);
		for (HashMap<Integer, Object> hashMap : inBox) {
			Event event = (Event) hashMap.get(Event.KEY_EVENT);
			Long questId = Long.parseLong(event.getParams().get(QUESTIONNAIRE_ID).toString());
			if (newQuestId.equals(questId)) {
				return true;
			}
		}
		return false;
	}

	public void doThings(HashMap<Integer, Object> fromServer) {
        System.out.println("doThings..."+getClass());
		// Pegar a lista de questionarios
		Event event = (Event) fromServer.get(Event.KEY_EVENT);

		long questId = Long.parseLong(event.getParams().get(QUESTIONNAIRE_ID).toString());

		if (logger.isDebugEnabled()) {
			logger.debug("Avaliando Questionário: " + questId);
		}

		QuestionnaireDao quest = new QuestionnaireDaoMySQL();

		// Pegar a lista de grupos do usuario
		ArrayList<Long> questUserIdList = quest.getQuestUserList(questId);

		// Pegar as questoes do questionario
		// Verificar de quais questoes estao corretas
		// Atualizar as notas
		QuestionnaireShiroUserDao questUser = new QuestionnaireShiroUserDaoMySQL();
		for (Long questUserId : questUserIdList) {
			Double score = quest.getScore(questUserId);
			questUser.updateScore(questUserId, score);

		}
		EventManagerProxySender sender = new EventManagerProxySender(
				Configurator.getProperty("PROXY_RECEIVER_HOST"),
				Integer.parseInt(Configurator
						.getProperty("PROXY_RECEIVER_PORT")));

		Map<Object, Object> params = new Hashtable<Object, Object>();
		params.put(QUESTIONNAIRE_ID, questId);
		Event eventToSend = new Event(
				Event.TYPE_UPDATE_QUESTIONNAIRE_STATISTICS, params);
		sender.update(eventToSend);
        ObserverDao updater = new ObseverDaoMySQL();
        updater.hitThePoint(getObserverName());

	}

	public static void main(String[] args) {
		QuestionnaireCreateObserver observer = new QuestionnaireCreateObserver();
	}

}
