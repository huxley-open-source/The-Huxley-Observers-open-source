package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.LanguageDao;

/** 
 * @author Romero Malaquias
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class LanguageDaoMySQL extends LanguageDao {
	
	public LanguageDaoMySQL() {
		
		SELECT_LANGUAGE = "SELECT * FROM language WHERE id = ?";
		LIST_LANGUAGE = "SELECT * FROM language";
			
	}
	
}
