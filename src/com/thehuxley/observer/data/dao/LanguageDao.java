package com.thehuxley.observer.data.dao;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.Language;
import com.thehuxley.observer.data.model.ResourcesUtil;

/** 
 * @author Romero Malaquias
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class LanguageDao {
	
	static Logger logger = LoggerFactory.getLogger(SubmissionDao.class);
	
	protected String SELECT_LANGUAGE;
	protected String UPDATE_LANGUAGE;
	protected String CREATE_LANGUAGE;
	protected String DELETE_LANGUAGE;
    protected String LIST_LANGUAGE;
	
	public Language getLanguageById(long id) {
		
		if (logger.isDebugEnabled()) {	
			logger.debug("Obtendo linguagem " + id + "...");
		}
		
		ResultSet rs = null;
		PreparedStatement statement = null;
			
		Language language = new Language();
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_LANGUAGE);
			}
			
			statement = Connector.getConnection().prepareStatement(SELECT_LANGUAGE);
			statement.setLong(1, id);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				language.setId(rs.getLong("id"));
				language.setVersion(rs.getLong("version"));
				language.setName(rs.getString("name"));
				language.setScript(rs.getString("script"));
				language.setExtension(rs.getString("extension"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}
		
		return language;
	}

    public ArrayList<Language> list() {

		if (logger.isDebugEnabled()) {
			logger.debug("Listando linguagens...");
		}

		ResultSet rs = null;
		PreparedStatement statement = null;
        ArrayList<Language> languageList = new ArrayList<Language>();

		Language language = new Language();
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(LIST_LANGUAGE);
			}

			statement = Connector.getConnection().prepareStatement(LIST_LANGUAGE);
			rs = statement.executeQuery();

			while (rs.next()) {
				language.setId(rs.getLong("id"));
				language.setVersion(rs.getLong("version"));
				language.setName(rs.getString("name"));
				language.setScript(rs.getString("script"));
				language.setExtension(rs.getString("extension"));
                languageList.add(language);
                language = new Language();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return languageList;
	}

}
