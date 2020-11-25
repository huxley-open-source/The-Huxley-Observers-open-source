package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.UserDao;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class UserDaoMySQL extends UserDao {
	
	public UserDaoMySQL() {
		
		SELECT_USER = "SELECT * FROM user WHERE id = ?";
		UPDATE_PROFILE = "update profile u set problems_tryed = (SELECT count(up.id) FROM user_problem up where up.user_id =u.user_id), problems_correct = (SELECT count(up.id) FROM user_problem up where up.user_id =u.user_id and up.status = 1) where u.user_id = ?";
			
	}

}
