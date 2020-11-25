package com.thehuxley.observer.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.Problem;
import com.thehuxley.observer.data.model.ResourcesUtil;

/** 
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class ProblemDao {
	
	static Logger logger = LoggerFactory.getLogger(SubmissionDao.class);
	
	protected String SELECT_PROBLEM;
	protected String UPDATE_PROBLEM;
	protected String CREATE_PROBLEM;
	protected String DELETE_PROBLEM;
	protected String FASTEST_UPDATE;
	
	public Problem getProblemById(long id) {
		
		if (logger.isDebugEnabled()) {	
			logger.debug("Obtendo problem " + id + "...");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
			
		Problem problem = new Problem();
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_PROBLEM);
			}
			
			statement = Connector.getConnection().prepareStatement(SELECT_PROBLEM);
			statement.setLong(1, id);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				problem.setId(rs.getLong("id"));
				problem.setVersion(rs.getLong("version"));
				problem.setTimeLimit(rs.getInt("time_limit"));
				problem.setEvaluationDetail(rs.getInt("evaluation_detail"));
				problem.setCode(rs.getInt("code"));
				problem.setLevel(rs.getInt("level"));
				problem.setNd(rs.getDouble("nd"));
				problem.setName(rs.getString("name"));
				problem.setStatus(rs.getString("status"));
				problem.setUserApprovedId(rs.getLong("user_approved_id"));
				problem.setUserSuggestId(rs.getLong("user_suggest_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}
		
		return problem;
	}
	
	public void deleteProblemById(long id) {
		PreparedStatement statement = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Deletando problem " + id + "...");
		}
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(DELETE_PROBLEM);
			}
			
			statement = Connector.getConnection().prepareStatement(DELETE_PROBLEM);
			statement.setLong(1, id);
			statement.executeUpdate();
			Connector.getConnection().commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
	
public void updateFastestSub(long id) {
	PreparedStatement statement = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando submissão mais rapida problem " + id + "...");
		}
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(FASTEST_UPDATE);
			}
			
			statement = Connector.getConnection().prepareStatement(FASTEST_UPDATE);
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
	
	public void createProblem(Problem problem) {
		PreparedStatement statement = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Criando problem...");
		}
		
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_PROBLEM);
			}
			
			statement = Connector.getConnection().prepareStatement(CREATE_PROBLEM);
			
			statement.setInt(1, problem.getTimeLimit());
			statement.setInt(2, problem.getEvaluationDetail());
			statement.setInt(3, problem.getCode());
			statement.setInt(5, problem.getLevel());
			statement.setDouble(6, problem.getNd());
			statement.setString(8, problem.getName());
			statement.setString(11, problem.getStatus());
			statement.setLong(12, problem.getUserApprovedId());
			statement.setLong(13, problem.getUserSuggestId());
			
			statement.executeUpdate();
			Connector.getConnection().commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
	
	public void updateProblem(Problem problem) {
		PreparedStatement statement = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando problem " + problem.getId() + "...");
		}
		
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_PROBLEM);
			}
			
			statement = Connector.getConnection().prepareStatement(UPDATE_PROBLEM);
			
			statement.setInt(1, problem.getTimeLimit());
			statement.setInt(2, problem.getEvaluationDetail());
			statement.setInt(3, problem.getCode());
			statement.setInt(5, problem.getLevel());
			statement.setDouble(6, problem.getNd());
			statement.setString(8, problem.getName());
			statement.setString(11, problem.getStatus());
			statement.setLong(12, problem.getUserApprovedId());
			statement.setLong(13, problem.getUserSuggestId());
			statement.setLong(14, problem.getId());
			
			statement.executeUpdate();
			Connector.getConnection().commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
}
