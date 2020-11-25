package com.thehuxley.observer;

import com.thehuxley.event.AbstractObserver;
import com.thehuxley.event.Event;
import com.thehuxley.observer.data.Configurator;
import com.thehuxley.observer.data.dao.QuestionnaireDao;
import com.thehuxley.observer.data.dao.mysql.QuestionnaireDaoMySQL;
import com.thehuxley.observer.data.model.QuestionnaireStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AchievementObserver extends AbstractObserver {
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
		return AchievementObserver.class.getName();
	}

	@Override
	protected int getEventMask() {
		return Event.TYPE_UPDATE_QUESTIONNAIRE_STATISTICS;
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
		Long newQuestId = (Long) newEvent.getParams().get(QUESTIONNAIRE_ID);

		for (HashMap<Integer, Object> hashMap : inBox) {
			Event event = (Event) hashMap.get(Event.KEY_EVENT);
			Long questId = (Long) event.getParams().get(QUESTIONNAIRE_ID);
			if (newQuestId.equals(questId)) {
				return true;
			}
		}
		return false;
	}

	public void doThings(HashMap<Integer, Object> fromServer) {
		// Pegar a lista de questionarios
		Event event = (Event) fromServer.get(Event.KEY_EVENT);

		long questId = (Long) event.getParams().get(QUESTIONNAIRE_ID);
		QuestionnaireDao quest = new QuestionnaireDaoMySQL();
		if (logger.isInfoEnabled()) {
			logger.info("Avaliando Questionário: " + questId);
		}
		// Pegar a lista de grupos do usuario
		ArrayList<Long> groupIdList = quest.getGroupByQuest(questId);

		// Pegar as questoes do questionario
		// Verificar de quais questoes estao corretas
		// Atualizar as notas
		for (Long groupId : groupIdList) {
			double userInGroup = quest.getUserCountByGroup(groupId);
			double userTryed = quest.getUserTryes(questId, groupId);
			double greaterSeven = quest.getGreaterSeven(questId, groupId);
			double scoreTotal = quest.getScoreSum(questId, groupId);
			double scoreAverage = scoreTotal / userTryed;
			double lessSeven = userTryed - greaterSeven;
			double localAverageNote = scoreTotal / userTryed;
			ArrayList<Double> scoreList = quest.getGroupScore(questId, groupId);
			double variance = 0;
			System.out.println("user in group: " + userInGroup);
			System.out.println("user tryed: " + userTryed);
			System.out.println("user greater seven: " + greaterSeven);
			System.out.println("user score total: " + scoreTotal);
			System.out.println("user score average: " + scoreAverage);
			System.out.println("user less seven: " + lessSeven);

			for (Double score : scoreList) {
				variance += Math.pow(localAverageNote - score, 2);
			}
			variance = variance / userTryed;
			Double standartDeviation = Math.sqrt(variance);
			QuestionnaireStatistics statistics = new QuestionnaireStatistics();
			statistics.setAverageNote(scoreAverage);
			statistics.setGreaterThenEqualsSeven(greaterSeven);
			statistics.setGroupId(groupId);
			statistics.setLessSeven(lessSeven);
			statistics.setQuestionnaireId(questId);
			statistics.setStandartDeviaton(standartDeviation);
			statistics.setTryPercentage(userTryed / userInGroup);
			statistics.setVersion(1);
			Long statisticsId = quest.getStatistics(questId, groupId);
			if (statisticsId != 0) {
				statistics.setId(statisticsId);
				quest.updateStatistics(statistics);
			} else {
				quest.saveStatistics(statistics);
			}

		}

	}

	public static void main(String[] args) throws Exception {
		AchievementObserver observer = new AchievementObserver();
//		Long[] ids = new Long[] { 1l, 2l, 3l, 4l };
//		Thread.sleep(2000);
//		EventManagerProxySender sender = new EventManagerProxySender(
//				"localhost", 50445);
//		for (int j = 0; j < 100; j++) {
//			for (int i = 0; i < ids.length; i++) {
//				Map<Object, Object> params = new Hashtable<Object, Object>();
//				params.put(QUESTIONNAIRE_ID, ids[i]);
//				Event event = new Event(
//						Event.TYPE_UPDATE_QUESTIONNAIRE_STATISTICS, params);
//				sender.update(event);
//			}
//		}
//		
//		Thread.sleep(10000);
//		for (int j = 0; j < 100; j++) {
//			for (int i = 0; i < 3; i++) {
//				Map<Object, Object> params = new Hashtable<Object, Object>();
//				params.put(QUESTIONNAIRE_ID, ids[i]);
//				Event event = new Event(
//						Event.TYPE_UPDATE_QUESTIONNAIRE_STATISTICS, params);
//				sender.update(event);
//			}
//		}
	}

}
