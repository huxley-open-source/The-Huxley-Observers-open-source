package com.thehuxley.observer.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.QuestionnaireShiroUser;
import com.thehuxley.observer.data.model.ResourcesUtil;

/** 
 * @author Romero B. de S. Malaquias
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class QuestionnaireShiroUserDao {
	
	static Logger logger = LoggerFactory.getLogger(QuestionnaireShiroUserDao.class);
	
	protected String SELECT_QUESTIONNAIRE;
	protected String UPDATE_QUESTIONNAIRE;
	protected String CREATE_QUESTIONNAIRE;
	protected String DELETE_QUESTIONNAIRE;
	protected String CORRECT_QUESTIONNAIRE;
	protected String UPDATE_SCORE;
	
	public QuestionnaireShiroUser getQuestionnaireShiroUserById(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		if (logger.isDebugEnabled()) {	
			logger.debug("Obtendo questionnaire " + id + "...");
		}
			
		QuestionnaireShiroUser questionnaire = new QuestionnaireShiroUser();
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_QUESTIONNAIRE);
			}
			
			statement = Connector.getConnection().prepareStatement(SELECT_QUESTIONNAIRE);
			statement.setLong(1, id);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				questionnaire.setId(rs.getLong("id"));
				questionnaire.setVersion(rs.getLong("version"));
				questionnaire.setQuestionnaireId(rs.getLong("questionnaire_id"));
				questionnaire.setUserId(rs.getLong("user_id"));
				questionnaire.setScore(rs.getDouble("score"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}
		
		return questionnaire;
	}
	
	public void deleteQuestionnaireShiroUserById(long id) {
		PreparedStatement statement = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Deletando questionnaire " + id + "...");
		}
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(DELETE_QUESTIONNAIRE);
			}
			
			statement = Connector.getConnection().prepareStatement(DELETE_QUESTIONNAIRE);
			statement.setLong(1, id);
			statement.executeUpdate();
			Connector.getConnection().commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
	
	public void createQuestionnaireShiroUser(QuestionnaireShiroUser questionnaire) {
		PreparedStatement statement = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Criando questionnaire...");
		}
		
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_QUESTIONNAIRE);
			}
			
			statement = Connector.getConnection().prepareStatement(CREATE_QUESTIONNAIRE);
			
			statement.setLong(1, questionnaire.getQuestionnaireId());
			statement.setLong(2, questionnaire.getUserId());
			statement.setDouble(3, questionnaire.getScore());

			statement.executeUpdate();
			Connector.getConnection().commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
	
	public void updateQuestionnaireShiroUser(QuestionnaireShiroUser questionnaire) {
		PreparedStatement statement = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando questionnaire " + questionnaire.getId() + "...");
		}
		
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_QUESTIONNAIRE);
			}
			
			statement = Connector.getConnection().prepareStatement(UPDATE_QUESTIONNAIRE);
			
			statement.setLong(1, questionnaire.getQuestionnaireId());
			statement.setLong(2, questionnaire.getUserId());
			statement.setDouble(3, questionnaire.getScore());

			
			statement.executeUpdate();
			Connector.getConnection().commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
	/**
	 * Essa função a partir de um id de uma submissão atualiza as notas de todos os questionários que dada submissão faz parte
	 * @param submissionId id da submissão
	 */
	public void correctQuestionnairesBySubmissionId(long submissionId){
		PreparedStatement statement = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando questionnaire_shiro_user ...");
		}
		
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(CORRECT_QUESTIONNAIRE);
			}
			
			statement = Connector.getConnection().prepareStatement(CORRECT_QUESTIONNAIRE);
			
			statement.setLong(1, submissionId);
			
			statement.executeUpdate();
//			Connector.getConnection().commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
	
	public void updateScore(long questId, Double score) {
		PreparedStatement statement = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando questionnaire " + questId + "... colocando o score " + score);
		}
		
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_SCORE);
			}
			
			statement = Connector.getConnection().prepareStatement(UPDATE_SCORE);
			
			statement.setDouble(1, score);
			statement.setLong(2, questId);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
}
