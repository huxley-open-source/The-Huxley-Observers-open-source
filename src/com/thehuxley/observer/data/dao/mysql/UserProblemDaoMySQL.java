package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.UserProblemDao;

/** 
 * @author Romero Malaquias
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class UserProblemDaoMySQL extends UserProblemDao {
	
	public UserProblemDaoMySQL() {
		
		SELECT_USER_PROBLEM = "SELECT * FROM user_problem WHERE user_id = ? and problem_id = ?";
		CREATE_USER_PROBLEM = "INSERT INTO user_problem (user_id,problem_id,status,version) VALUES (?,?,?,0)";
		UPDATE_USER_PROBLEM = "UPDATE user_problem set status = ? WHERE id = ?";
        UPDATE_USER_PROBLEM_SIMILARITY = "UPDATE user_problem set similarity = ? WHERE user_id = ? and problem_id = ?";
				 
			
	}
	
}
