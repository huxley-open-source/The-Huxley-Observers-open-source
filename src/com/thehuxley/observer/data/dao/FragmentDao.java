package com.thehuxley.observer.data.dao;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.Fragment;
import com.thehuxley.observer.data.model.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class FragmentDao {

	static Logger logger = LoggerFactory.getLogger(FragmentDao.class);

	protected String CREATE_FRAGMENT;


	public long createFragment(Fragment fragment) {
		PreparedStatement statement = null;
        int rowsChanged = -1;
		if (logger.isDebugEnabled()) {
			logger.debug("Criando plagio...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(CREATE_FRAGMENT);
			}

			statement = Connector.getConnection().prepareStatement(
                    CREATE_FRAGMENT);

			statement.setLong(1, fragment.getNumberOfLines());
			statement.setDouble(2, fragment.getPercentage());
            statement.setDouble(3, fragment.getPlagiumId());
            statement.setLong(4, fragment.getStartLine1());
            statement.setLong(5, fragment.getStartLine2());
            statement.setLong(6, fragment.getFragmentsIdx());

			rowsChanged = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}

        return rowsChanged ;
    }
}

