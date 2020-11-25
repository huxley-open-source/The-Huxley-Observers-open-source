package com.thehuxley.observer.data.dao;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.Questionnaire;
import com.thehuxley.observer.data.model.QuestionnaireStatistics;
import com.thehuxley.observer.data.model.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class ObserverDao {

	static Logger logger = LoggerFactory.getLogger(ObserverDao.class);


	protected String UPDATE_OBSERVER;

	public void hitThePoint(String name) {

		PreparedStatement statement = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando observer " + name
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_OBSERVER);
			}

			statement = Connector.getConnection().prepareStatement(
					UPDATE_OBSERVER);


			statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			statement.setString(2, name);


			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}
	}

}