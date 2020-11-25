package com.thehuxley.observer.data.dao;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.Plagium;
import com.thehuxley.observer.data.model.Problem;
import com.thehuxley.observer.data.model.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class PlagiumDao {

	static Logger logger = LoggerFactory.getLogger(PlagiumDao.class);

	protected String CREATE_PLAGIUM;
    protected String LAST_ID;
    protected String DELETE_BY_PROBLEM_AND_LANGUAGE;

	public long getLastId() {
		PreparedStatement statement = null;
		ResultSet rs = null;
        long id = 0;

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo ultimo id do plagio ...");
		}

		
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(LAST_ID);
			}

			statement = Connector.getConnection().prepareStatement(
                    LAST_ID);
			rs = statement.executeQuery();

			if (rs.next()) {
				id = rs.getLong("last_id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return id;
	}


	public long createPlagium(Plagium plagium) {
		PreparedStatement statement = null;
        int rowsChanged = -1;
		if (logger.isDebugEnabled()) {
			logger.debug("Criando plagio...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(CREATE_PLAGIUM);
			}

			statement = Connector.getConnection().prepareStatement(
					CREATE_PLAGIUM);

			statement.setLong(1, plagium.getId());
			statement.setDouble(2, plagium.getPercentage());
            statement.setLong(3, plagium.getSubmission1Id());
            statement.setLong(4, plagium.getSubmission2Id());
			rowsChanged = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}

        return rowsChanged ;
    }

    public Problem deletePlagiumByProblemAndLanguage(long languageId, long problemId) {

        if (logger.isDebugEnabled()) {
            logger.debug("Deletando plagium de liguagem" + languageId + " e problema " + problemId + "...");
        }
        PreparedStatement statement = null;
        ResultSet rs = null;

        Problem problem = new Problem();
        try {

            if (logger.isDebugEnabled()) {
                logger.debug(DELETE_BY_PROBLEM_AND_LANGUAGE);
            }

            statement = Connector.getConnection().prepareStatement(DELETE_BY_PROBLEM_AND_LANGUAGE);
            statement.setLong(1, problemId);
            statement.setLong(2, languageId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ResourcesUtil.closeResultSet(rs);
            ResourcesUtil.closeStatement(statement);
        }

        return problem;
    }
}

