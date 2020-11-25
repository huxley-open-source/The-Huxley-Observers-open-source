package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.TestCaseDao;

/** 
 * @author Romero B. de S. Malaquias
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class TestCaseDaoMySQL extends TestCaseDao {
	
	public TestCaseDaoMySQL() {
		LIST_BY_PROBLEM = "SELECT * FROM test_case WHERE problem_id = ? LIMIT " + LIMIT +" offset ?";
			
	}
	
}
