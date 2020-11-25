package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.Configurator;
import com.thehuxley.observer.data.dao.SubmissionDao;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public class SubmissionDaoMySQL extends SubmissionDao {
	
	public SubmissionDaoMySQL() {
		SELECT_TOP_WAITING = "SELECT * FROM submission AS s, problem AS p, language AS l WHERE  p.id = s.problem_id AND l.id = s.language_id AND s.evaluation = \"WAITING\" ORDER BY s.submission_date LIMIT 0," + Configurator.getProperties().getProperty("waiting_queue.size");
		UPDATE_WAITING_EVALUATING = "UPDATE submission SET evaluation = \"EVALUATING\" WHERE id = ?";
		CREATE_SUBMISSION = "INSERT INTO submission (problem_id, submission, submission_dir, evaluation, submission_date, detailed_log, diff_file, language_id, tries, output, user_id, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		DELETE_SUBMISSION = "DELETE submission WHERE id = ?";
		SELECT_SUBMISSION = "SELECT * FROM submission WHERE id = ?";
		UPDATE_SUBMISSION = "UPDATE submission SET evaluation = ?, time = ? WHERE id = ?";
        MARK_ALL_NOT_MATCHED = "update submission s set s.plagium_status = 3 where s.plagium_status = 1";
        GET_FROM_USER_PROBLEM_ATTEMPTS = "Select * from submission where user_id = ? and problem_id = ? and tries = ?";
        UPDATE_PLAGIUM_STATUS = "update submission s set s.plagium_status = ? where s.id = ?";
        GET_DIR_LIST_BY_CLUSTER= "Select concat(s.problem_id,'/',s.user_id,'/language/',s.tries,'/',s.submission) path from submission s where problem_id = ? and language_id = ? and user_id in (select cp.user_id from cluster_permissions cp left join cluster c on c.id=cp.group_id where c.institution_id = ?) and id in (SELECT distinct submission1_id FROM plagium p where percentage > ? and submission1_id not in (SELECT distinct submission2_id FROM plagium p where percentage > ?)) and YEAR(s.submission_date) > ?";
	}
	
}