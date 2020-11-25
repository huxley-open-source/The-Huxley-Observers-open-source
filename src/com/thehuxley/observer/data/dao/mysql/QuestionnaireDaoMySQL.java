package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.QuestionnaireDao;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class QuestionnaireDaoMySQL extends QuestionnaireDao {
	
	public QuestionnaireDaoMySQL() {
		
		CREATE_QUESTIONNAIRE = "INSERT INTO questionnaire (start_date, evaluation_detail, score, end_date, title, description) VALUES (?, ?, ?, ?, ?, ?)";
		DELETE_QUESTIONNAIRE = "DELETE questionnaire WHERE id = ?";
		SELECT_QUESTIONNAIRE = "SELECT * FROM questionnaire WHERE id = ?";
		UPDATE_QUESTIONNAIRE = "UPDATE questionnaire SET start_date = ?, evaluation_detail = ?, score = ?, end_date = ?, title =?, description = ? WHERE id = ?";
		SELECT_GROUP_BY_QUESTIONNAIRE = "SELECT cluster_id group_id FROM questionnaire_cluster q where q.questionnaire_groups_id = ?";
		USERS_IN_GROUP = "SELECT count(Distinct cp.user_id) user_count from cluster_permissions cp where cp.group_id = ? and cp.permission = 0";
		USERS_TRYED = "SELECT count(Distinct cp.user_id) try_count from submission s, cluster_permissions cp, questionnaire q left join questionnaire_problem qp on q.id = qp.questionnaire_id where q.id = ? and cp.group_id = ? and s.problem_id = qp.problem_id and s.user_id = cp.user_id and cp.permission = 0";
		GREATER_SEVEN = "SELECT count(Distinct cp.user_id) greater_seven from cluster_permissions cp, questionnaire_shiro_user qu where cp.group_id = ? and cp.permission = 0 and qu.questionnaire_id = ? and qu.score >= 7 and qu.user_id = cp.user_id";
		SCORE_AVERAGE = "SELECT sum(qu.score) total_score from cluster_permissions cp, questionnaire_shiro_user qu where cp.group_id = ? and cp.permission = 0 and qu.questionnaire_id = ? and qu.user_id = cp.user_id";
		LIST_SCORE_QUEST = "SELECT qu.score score from cluster_permissions cp, questionnaire_shiro_user qu where cp.group_id = ? and cp.permission = 0 and qu.questionnaire_id = ? and qu.user_id = cp.user_id";
		FIND_QUEST_STATISTICS = "SELECT id statistics_id FROM questionnaire_statistics qs where qs.group_id = ? and qs.questionnaire_id = ?";
		INSERT_STATISTICS = "INSERT INTO questionnaire_statistics (version,average_note,greater_then_equals_seven,group_id,less_seven,questionnaire_id,standart_deviaton,try_percentage) values (0,?,?,?,?,?,?,?)";
		UPDATE_STATISTICS = "UPDATE questionnaire_statistics set average_note=?,greater_then_equals_seven=?,group_id=?,less_seven=?,questionnaire_id=?,standart_deviaton=?,try_percentage=? where id = ?";
		//Pega a lista de questionnaires shiro user a partir de um usuario
		GET_QUEST_USER = "SELECT qu.id quest_user_id FROM questionnaire_shiro_user qu where qu.questionnaire_id = ?";
		GET_SCORE_BY_QUEST_USER = "Select qp.score score FROM (questionnaire q right join questionnaire_shiro_user qu on q.id = qu.questionnaire_id right join questionnaire_problem qp on q.id = qp.questionnaire_id left join submission s on qp.problem_id = s.problem_id and qu.user_id = s.user_id) where qu.id = ? and s.evaluation = 'CORRECT' and s.submission_date < q.end_date group by qp.problem_id";
	}
}
