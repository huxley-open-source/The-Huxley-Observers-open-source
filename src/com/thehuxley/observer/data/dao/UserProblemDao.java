package com.thehuxley.observer.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.ResourcesUtil;
import com.thehuxley.observer.data.model.UserProblem;

/** 
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class UserProblemDao {
	
	static Logger logger = LoggerFactory.getLogger(SubmissionDao.class);
	
	protected String SELECT_USER_PROBLEM;
	protected String UPDATE_USER_PROBLEM;
	protected String CREATE_USER_PROBLEM;
    protected String UPDATE_USER_PROBLEM_SIMILARITY;
		
	public UserProblem getByUserAndProblemId(long userId, long problemId) {
		
		if (logger.isDebugEnabled()) {	
			logger.debug("Obtendo o user problem para o usuario " + userId + " e problema" + problemId + "...");
		}
			
		UserProblem userProblem = null;
		ResultSet rs = null;
		PreparedStatement statement = null;	
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_USER_PROBLEM);
			}
			
			statement = Connector.getConnection().prepareStatement(SELECT_USER_PROBLEM);
			statement.setLong(1, userId);
			statement.setLong(2, problemId);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				userProblem = new UserProblem();
				userProblem.setId(rs.getLong("id"));
				userProblem.setVersion(rs.getLong("version"));
				userProblem.setProblemId(rs.getLong("problem_id"));
				userProblem.setUserId(rs.getLong("user_id"));
				userProblem.setStatus(rs.getInt("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}
		
		return userProblem;
	}
	
	
	
	public void createUserProblem(long userId, long problemId, int status) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Criando user problem...");
		}
		PreparedStatement statement = null;	
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(CREATE_USER_PROBLEM);
			}
			
			statement = Connector.getConnection().prepareStatement(CREATE_USER_PROBLEM);
			
			statement.setLong(1, userId);
			statement.setLong(2, problemId);
			statement.setInt(3, status);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}
	
	public void updateProblem(long userId, long problemId, int status) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando user problem ...");
		}
		PreparedStatement statement = null;	
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_USER_PROBLEM);
			}
			UserProblem userProblem = getByUserAndProblemId(userId,problemId); 
			if(userProblem == null){
				createUserProblem(userId, problemId, status);
			}else if (status == 1 && userProblem.getStatus() != 1 ){
				statement = Connector.getConnection().prepareStatement(UPDATE_USER_PROBLEM);
				
				statement.setInt(1, status);
				statement.setLong(2, userProblem.getId());
				
				
				
				statement.executeUpdate();
	
			}
					} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
	}

    public void updateUserProblemSimilarity(long userId, long problemId, int similarity) {

        if (logger.isDebugEnabled()) {
            logger.debug("Atualizando user problem ...");
        }
        PreparedStatement statement = null;
        try {

            if (logger.isDebugEnabled()) {
                logger.debug(UPDATE_USER_PROBLEM_SIMILARITY);
            }
            statement = Connector.getConnection().prepareStatement(UPDATE_USER_PROBLEM_SIMILARITY);
            statement.setInt(1, similarity);
            statement.setLong(2, userId);
            statement.setLong(3, problemId);
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ResourcesUtil.closeStatement(statement);
        }
    }
}
