package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.ProblemDao;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class ProblemDaoMySQL extends ProblemDao {
	
	public ProblemDaoMySQL() {
		
		CREATE_PROBLEM = "INSERT INTO problem (time_limit, evaluation_detail, code, input, level, nd, description, name, problem_root, output, status, user_approved_id, user_suggest_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		DELETE_PROBLEM = "DELETE problem WHERE id = ?";
		SELECT_PROBLEM = "SELECT * FROM problem WHERE id = ?";
		UPDATE_PROBLEM = "UPDATE problem SET time_limit = ?, evaluation_detail = ?, code = ?, input = ?, level = ?, nd = ?, description = ?, name = ?, problem_root = ?, output = ?, status = ?, user_approved_id = ?, user_suggest_id = ? WHERE = ?";
		FASTEST_UPDATE = "UPDATE problem u set u.fastest_submision_id = (SELECT s.id FROM submission s where s.evaluation='CORRECT' and s.problem_id = u.id and s.time like '%00.%' order by time asc limit 1) where u.id=?";
			
	}
	
}
