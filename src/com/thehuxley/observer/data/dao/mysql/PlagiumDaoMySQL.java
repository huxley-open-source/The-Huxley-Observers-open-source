package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.PlagiumDao;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class PlagiumDaoMySQL extends PlagiumDao {

	public PlagiumDaoMySQL() {

        CREATE_PLAGIUM = "Insert into plagium (id,version,percentage,submission1_id,submission2_id) values (?,1,?,?,?)";
        LAST_ID = "SELECT id as last_id FROM plagium ORDER BY id DESC LIMIT 1";
        DELETE_BY_PROBLEM_AND_LANGUAGE = "Delete from plagium where submission1_id in (select s.id from submission s where s.problem_id = ? and s.language_id = ?)";


	}
}
