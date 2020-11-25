package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.QuestionnaireShiroUserDao;

/** 
 * @author Romero B. de S. Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public class QuestionnaireShiroUserDaoMySQL extends QuestionnaireShiroUserDao {
	
	public QuestionnaireShiroUserDaoMySQL() {
		
		CREATE_QUESTIONNAIRE = "INSERT INTO questionnaire_shiro_user (questionnaire_id, user_id, score) VALUES (?, ?, ?)";
		DELETE_QUESTIONNAIRE = "DELETE questionnaire_shiro_user WHERE id = ?";
		SELECT_QUESTIONNAIRE = "SELECT * FROM questionnaire_shiro_user WHERE id = ?";
		UPDATE_QUESTIONNAIRE = "UPDATE questionnaire SET questionnaire_id = ?, user_id = ?, score = ? WHERE id = ?";
		CORRECT_QUESTIONNAIRE = "UPDATE questionnaire_shiro_user qu3 LEFT JOIN (SELECT (qu2.id)q,sum(qp2.score)s from questionnaire_problem qp2 join questionnaire_shiro_user qu2 on qp2.questionnaire_id = qu2.questionnaire_id, submission sub where qp2.id in (SELECT qp.id FROM ((questionnaire q JOIN questionnaire_shiro_user qu on q.id = qu.questionnaire_id) JOIN questionnaire_problem qp on q.id = qp.questionnaire_id), submission s where s.evaluation = 'CORRECT'and s.submission_date < q.end_date and s.problem_id = qp.problem_id and s.user_id = qu.user_id and qu.id in (SELECT qu4.id from questionnaire_shiro_user qu4 join questionnaire_problem qp4 on qu4.questionnaire_id = qp4.questionnaire_id where qu4.user_id = sub.user_id and qp4.problem_id = sub.problem_id) group by qp.id ) and qu2.user_id = sub.user_id and sub.id = ? group by qu2.id) as atual on qu3.id = atual.q SET qu3.score = atual.s where atual.q = qu3.id";
		UPDATE_SCORE = "UPDATE questionnaire_shiro_user SET score = ? WHERE id = ?";
			
	}
}
