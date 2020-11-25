package com.thehuxley.observer.data.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.Questionnaire;
import com.thehuxley.observer.data.model.QuestionnaireStatistics;
import com.thehuxley.observer.data.model.ResourcesUtil;

/**
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class QuestionnaireDao {

	static Logger logger = LoggerFactory.getLogger(QuestionnaireDao.class);

	protected String SELECT_QUESTIONNAIRE;
	protected String UPDATE_QUESTIONNAIRE;
	protected String CREATE_QUESTIONNAIRE;
	protected String DELETE_QUESTIONNAIRE;
	protected String SELECT_GROUP_BY_QUESTIONNAIRE;
	protected String USERS_IN_GROUP;
	protected String USERS_TRYED;
	protected String GREATER_SEVEN;
	protected String SCORE_AVERAGE;
	protected String LIST_SCORE_QUEST;
	protected String FIND_QUEST_STATISTICS;
	protected String UPDATE_STATISTICS;
	protected String INSERT_STATISTICS;
	protected String GET_QUEST_USER;
	protected String GET_SCORE_BY_QUEST_USER;

	public Questionnaire getQuestionnaireById(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo questionnaireShiroUser " + id + "...");
		}

		Questionnaire questionnaire = new Questionnaire();
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_QUESTIONNAIRE);
			}

			statement = Connector.getConnection().prepareStatement(
					SELECT_QUESTIONNAIRE);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			if (rs.next()) {
				questionnaire.setId(rs.getLong("id"));
				questionnaire.setVersion(rs.getLong("version"));
				questionnaire.setStartDate(rs.getDate("start_date"));
				questionnaire.setEvaluationDetail(rs
						.getInt("evaluation_detail"));
				questionnaire.setScore(rs.getDouble("score"));
				questionnaire.setEndDate(rs.getDate("end_date"));
				questionnaire.setTitle(rs.getString("title"));
				questionnaire.setDescription(rs.getString("description"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return questionnaire;
	}

	public void deleteQuestionnaireById(long id) {
		PreparedStatement statement = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Deletando questionnaire " + id + "...");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug(DELETE_QUESTIONNAIRE);
			}

			statement = Connector.getConnection().prepareStatement(
					DELETE_QUESTIONNAIRE);
			statement.setLong(1, id);
			statement.executeUpdate();
			Connector.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}
	}

	public void createQuestionnaire(Questionnaire questionnaire) {
		PreparedStatement statement = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Criando questionnaire...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_QUESTIONNAIRE);
			}

			statement = Connector.getConnection().prepareStatement(
					CREATE_QUESTIONNAIRE);

			statement.setDate(1, new Date(questionnaire.getStartDate()
					.getTime()));
			statement.setInt(2, questionnaire.getEvaluationDetail());
			statement.setDouble(3, questionnaire.getScore());
			statement
					.setDate(4, new Date(questionnaire.getEndDate().getTime()));
			statement.setString(5, questionnaire.getTitle());
			statement.setString(6, questionnaire.getDescription());

			statement.executeUpdate();
			Connector.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}
	}

	public void updateQuestionnaire(Questionnaire questionnaire) {
		PreparedStatement statement = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando questionnaire " + questionnaire.getId()
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_QUESTIONNAIRE);
			}

			statement = Connector.getConnection().prepareStatement(
					UPDATE_QUESTIONNAIRE);

			statement.setDate(1, new Date(questionnaire.getStartDate()
					.getTime()));
			statement.setInt(2, questionnaire.getEvaluationDetail());
			statement.setDouble(3, questionnaire.getScore());
			statement
					.setDate(4, new Date(questionnaire.getEndDate().getTime()));
			statement.setString(5, questionnaire.getTitle());
			statement.setString(6, questionnaire.getDescription());
			statement.setLong(7, questionnaire.getId());

			statement.executeUpdate();
			Connector.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}
	}

	public ArrayList<Long> getGroupByQuest(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Long> questList = new ArrayList<Long>();
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo grupos do questionario: " + id + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_GROUP_BY_QUESTIONNAIRE);
			}

			statement = Connector.getConnection().prepareStatement(
					SELECT_GROUP_BY_QUESTIONNAIRE);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			while (rs.next()) {
				questList.add(rs.getLong("group_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return questList;
	}

	public Long getUserCountByGroup(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		long count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo user count do grupo " + id + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(USERS_IN_GROUP);
			}

			statement = Connector.getConnection().prepareStatement(
					USERS_IN_GROUP);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getLong("user_count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return count;
	}

	public Long getUserTryes(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		long count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo user tryes do grupo " + groupId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(USERS_TRYED);
			}

			statement = Connector.getConnection().prepareStatement(USERS_TRYED);
			statement.setLong(1, questId);
			statement.setLong(2, groupId);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getLong("try_count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return count;
	}

	public Long getGreaterSeven(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		long count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo notas maiores que 7 do grupo " + groupId
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(GREATER_SEVEN);
			}

			statement = Connector.getConnection().prepareStatement(
					GREATER_SEVEN);
			statement.setLong(1, groupId);
			statement.setLong(2, questId);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getLong("greater_seven");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return count;
	}

	public Double getScoreSum(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		double count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo a soma das notas do grupo " + groupId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SCORE_AVERAGE);
			}

			statement = Connector.getConnection().prepareStatement(
					SCORE_AVERAGE);
			statement.setLong(1, groupId);
			statement.setLong(2, questId);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getDouble("total_score");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return count;
	}

	public ArrayList<Double> getGroupScore(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Double> questList = new ArrayList<Double>();
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo notas do questionario: " + questId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(LIST_SCORE_QUEST);
			}

			statement = Connector.getConnection().prepareStatement(
					LIST_SCORE_QUEST);
			statement.setLong(1, groupId);
			statement.setLong(2, questId);
			rs = statement.executeQuery();
			while (rs.next()) {
				questList.add(rs.getDouble("score"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return questList;
	}

	public Long getStatistics(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		long count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo statistics do grupo " + groupId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(FIND_QUEST_STATISTICS);
			}

			statement = Connector.getConnection().prepareStatement(
					FIND_QUEST_STATISTICS);
			statement.setLong(1, groupId);
			statement.setLong(2, questId);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getLong("statistics_id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return count;
	}

	public void updateStatistics(QuestionnaireStatistics statistics) {
		PreparedStatement statement = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando questionnaire " + statistics.getId()
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_QUESTIONNAIRE);
			}

			statement = Connector.getConnection().prepareStatement(
					UPDATE_STATISTICS);

			statement.setDouble(1, statistics.getAverageNote());
			statement.setDouble(2, statistics.getGreaterThenEqualsSeven());
			statement.setLong(3, statistics.getGroupId());
			statement.setDouble(4, statistics.getLessSeven());
			statement.setLong(5, statistics.getQuestionnaireId());
			statement.setDouble(6, statistics.getStandartDeviaton());
			statement.setDouble(7, statistics.getTryPercentage());
			statement.setLong(8, statistics.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}
	}

	public void saveStatistics(QuestionnaireStatistics statistics) {
		PreparedStatement statement = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Criando statisticas para o questionnaire "
					+ statistics.getId() + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(INSERT_STATISTICS);
			}

			statement = Connector.getConnection().prepareStatement(
					INSERT_STATISTICS);

			statement.setDouble(1, statistics.getAverageNote());
			statement.setDouble(2, statistics.getGreaterThenEqualsSeven());
			statement.setLong(3, statistics.getGroupId());
			statement.setDouble(4, statistics.getLessSeven());
			statement.setLong(5, statistics.getQuestionnaireId());
			statement.setDouble(6, statistics.getStandartDeviaton());
			statement.setDouble(7, statistics.getTryPercentage());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}
	}

	public ArrayList<Long> getQuestUserList(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Long> questList = new ArrayList<Long>();
		if (logger.isDebugEnabled()) {
			logger.debug("Questionnarios de usuario do questionario: " + id
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(GET_QUEST_USER);
			}

			statement = Connector.getConnection().prepareStatement(
					GET_QUEST_USER);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			while (rs.next()) {
				questList.add(rs.getLong("quest_user_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}
		return questList;
	}

	public double getScore(long questUserId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		double score = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Problemas corretos para questionario usuario: "
					+ questUserId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(GET_SCORE_BY_QUEST_USER);
			}

			statement = Connector.getConnection().prepareStatement(
					GET_SCORE_BY_QUEST_USER);
			statement.setLong(1, questUserId);
			rs = statement.executeQuery();
			
			while (rs.next()) {
				System.out.println("passei aqui");
				System.out.println(rs.getDouble("score"));
				score += rs.getDouble("score");
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Score= "
						+ score + "...");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}
		return score;
	}

}
