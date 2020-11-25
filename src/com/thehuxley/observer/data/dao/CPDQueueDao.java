package com.thehuxley.observer.data.dao;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.CPDQueue;
import com.thehuxley.observer.data.model.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class CPDQueueDao {

	static Logger logger = LoggerFactory.getLogger(CPDQueueDao.class);

	protected String LIST_CPDQUEUE;
	protected String DELETE_CPDQUEUE;
    protected String DELETE_CPDQUEUE_BY_LANGUAGE_AND_PROBLEM;

    public void deleteCPDQueue(long id) {
        PreparedStatement statement = null;

        if (logger.isDebugEnabled()) {
            logger.debug("Deletando cpdqueue " + id + "...");
        }

        try {
            if (logger.isDebugEnabled()) {
                logger.debug(DELETE_CPDQUEUE);
            }
            statement = Connector.getConnection().prepareStatement(DELETE_CPDQUEUE);
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourcesUtil.closeStatement(statement);
        }
    }
    public void deleteCPDQueueByLanguageAndProblem(long problemId, String languageName) {
        PreparedStatement statement = null;

        if (logger.isDebugEnabled()) {
            logger.debug("Deletando cpdqueue problem: " + problemId + " language: " + languageName + "...");
        }

        try {
            if (logger.isDebugEnabled()) {
                logger.debug(DELETE_CPDQUEUE_BY_LANGUAGE_AND_PROBLEM);
            }
            statement = Connector.getConnection().prepareStatement(DELETE_CPDQUEUE_BY_LANGUAGE_AND_PROBLEM);
            statement.setLong(1, problemId);
            statement.setString(2, languageName);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourcesUtil.closeStatement(statement);
        }
    }

	public ArrayList<CPDQueue> listCPDQueue() {
		PreparedStatement statement = null;
        ResultSet rs = null;
        ArrayList<CPDQueue> cpdQueueList = new ArrayList<CPDQueue>();
		if (logger.isDebugEnabled()) {
			logger.debug("Listando CPDQueue");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug(LIST_CPDQUEUE);
			}

			statement = Connector.getConnection().prepareStatement(LIST_CPDQUEUE);
            rs = statement.executeQuery();
			while (rs.next()) {
                CPDQueue cpdQueue = new CPDQueue();
                cpdQueue.setId(rs.getLong("id"));
                cpdQueue.setLanguage(rs.getString("language"));
                cpdQueue.setProblemId(rs.getInt("problem_id"));
                cpdQueue.setInstitutionId(rs.getInt("institution_id"));
                cpdQueueList.add(cpdQueue);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}
        return cpdQueueList;
	}

}
