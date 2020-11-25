package com.thehuxley.observer.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.ResourcesUtil;
import com.thehuxley.observer.data.model.User;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class UserDao {

static Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	protected String SELECT_USER;
	protected String UPDATE_PROFILE;
	
	public User getUserById(long id) {
		
		if (logger.isDebugEnabled()) {	
			logger.debug("Obtendo user " + id + "...");
		}
		ResultSet rs = null;
		PreparedStatement statement = null;	
		User user = new User();
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_USER);
			}
			
			statement = Connector.getConnection().prepareStatement(SELECT_USER);
			statement.setLong(1, id);
			rs = statement.executeQuery(SELECT_USER);
			
			if (rs.next()) {
				user.setId(rs.getLong("id"));
				user.setVersion(rs.getLong("version"));
				user.setPasswordHash(rs.getString("password_hash"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setStatus(rs.getString("status"));
				user.setLastLogin(rs.getDate("last_login"));
				user.setTopCoderPosition(rs.getInt("top_coder_position"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}
		
		return user;
	}
	public void updateProfile(long userId){
		if (logger.isDebugEnabled()) {	
			logger.debug("Obtendo user " + userId + "...");
		}
		PreparedStatement statement = null;	
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_PROFILE);
			}
			
			statement = Connector.getConnection().prepareStatement(UPDATE_PROFILE);
			statement.setLong(1, userId);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeStatement(statement);
		}
		
	}
}
