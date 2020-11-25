package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.ObserverDao;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class ObseverDaoMySQL extends ObserverDao {

	public ObseverDaoMySQL() {

		UPDATE_OBSERVER = "UPDATE observer SET last_seen = ? WHERE name = ?";

	}
}