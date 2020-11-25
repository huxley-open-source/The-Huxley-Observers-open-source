package com.thehuxley.observer.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.thehuxley.observer.data.dao.mysql.UserProblemDaoMySQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.ResourcesUtil;
import com.thehuxley.observer.data.model.Submission;
import com.thehuxley.observer.data.model.UserProblem;

/**
 * @author Marcio Augusto Guimarães
 * @author Rodrigo Paes
 * @authir Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class SubmissionDao {

	protected String SELECT_TOP_WAITING;
	protected String UPDATE_WAITING_EVALUATING;
	protected String CREATE_SUBMISSION;
	protected String DELETE_SUBMISSION;
	protected String SELECT_SUBMISSION;
	protected String UPDATE_SUBMISSION;
    protected String MARK_ALL_NOT_MATCHED;
    protected String GET_FROM_USER_PROBLEM_ATTEMPTS;
    protected String UPDATE_PLAGIUM_STATUS;
    protected String GET_DIR_LIST_BY_CLUSTER;


	static Logger logger = LoggerFactory.getLogger(SubmissionDao.class);

	public List<Submission> selectTopWaiting() {
		ResultSet rs = null;
		Statement statement = null;
		ArrayList<Submission> topList = new ArrayList<Submission>();

		if (logger.isInfoEnabled()) {
			logger.info("Obtendo submissões para correção...");
		}

		try {
			statement = Connector.getConnection().createStatement();

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_TOP_WAITING);
			}

			rs = statement.executeQuery(SELECT_TOP_WAITING);

			while (rs.next()) {
				Submission submission = new Submission();
				submission.setId(rs.getLong("s.id"));
				submission.setVersion(rs.getLong("s.version"));
				submission.setProblemId(rs.getLong("s.problem_id"));
				submission.setSubmission(rs.getString("s.submission"));
				submission.setEvaluation(rs.getString("s.evaluation"));
				submission.setSubmissionDate(rs
						.getTimestamp("s.submission_date"));
				submission.setDetailedLog(rs.getBoolean("s.detailed_log"));
				submission.setDiffFile(rs.getString("s.diff_file"));
				submission.setLinguageId(rs.getLong("s.language_id"));
				submission.setTries(rs.getInt("s.tries"));
				submission.setOutput(rs.getString("s.output"));
				submission.setUserId(rs.getLong("s.user_id"));
				submission.setTime(rs.getString("s.time"));
				submission.getProblem().setId(rs.getLong("p.id"));
				submission.getProblem().setVersion(rs.getLong("p.version"));
				submission.getProblem().setTimeLimit(rs.getInt("p.time_limit"));
				submission.getProblem().setEvaluationDetail(
						rs.getInt("p.evaluation_detail"));
				submission.getProblem().setCode(rs.getInt("p.code"));
				submission.getProblem().setLevel(rs.getInt("p.level"));
				submission.getProblem().setNd(rs.getDouble("p.nd"));
				submission.getProblem().setName(rs.getString("p.name"));
				submission.getProblem().setStatus(rs.getString("p.status"));
				submission.getProblem().setUserApprovedId(
						rs.getLong("p.user_approved_id"));
				submission.getProblem().setUserSuggestId(
						rs.getLong("p.user_suggest_id"));
				// submission.getProblem().setScreeshot(rs.getBlob(30));

				/*
				 * submission.getLanguage().setId(rs.getLong("l.id"));
				 * submission.getLanguage().setVersion(rs.getLong("l.version"));
				 * submission
				 * .getLanguage().setExecParams(rs.getString("l.exec_params"));
				 * submission
				 * .getLanguage().setPlagConfig(rs.getString("l.plag_config"));
				 * submission.getLanguage().setName(rs.getString("l.name"));
				 * submission
				 * .getLanguage().setCompileParams(rs.getString("l.compile_params"
				 * ));
				 * submission.getLanguage().setCompile(rs.getString("l.compiler"
				 * ));
				 * submission.getLanguage().setScript(rs.getString("l.script"));
				 */
				updateStatusForEvaluating(submission);

				topList.add(submission);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}
		return topList;
	}

	public void updateStatusForEvaluating(Submission submission) {
		PreparedStatement statement = null;
		try {

			if (logger.isInfoEnabled()) {
				logger.info("Alterando o status da submissão "
						+ submission.getId() + " para \"EVALUATING\".");
			}

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_WAITING_EVALUATING);
			}

			statement = Connector.getConnection().prepareStatement(
					UPDATE_WAITING_EVALUATING);
			statement.setLong(1, submission.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}

	}

	public Submission getSubmissionById(long id) {

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo submission " + id + "...");
		}
		ResultSet rs = null;
		PreparedStatement statement = null;

		Submission submission = new Submission();

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_SUBMISSION);
			}

			statement = Connector.getConnection().prepareStatement(
					SELECT_SUBMISSION);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			if (rs.next()) {
				submission.setId(rs.getLong("id"));
				submission.setVersion(rs.getLong("version"));
				submission.setProblemId(rs.getLong("problem_id"));
				submission.setSubmission(rs.getString("submission"));
				submission.setEvaluation(rs.getString("evaluation"));
				submission
						.setSubmissionDate(rs.getTimestamp("submission_date"));
				submission.setDetailedLog(rs.getBoolean("detailed_log"));
				submission.setDiffFile(rs.getString("diff_file"));
				submission.setLinguageId(rs.getLong("language_id"));
				submission.setTries(rs.getInt("tries"));
				submission.setOutput(rs.getString("output"));
				submission.setUserId(rs.getLong("user_id"));
				submission.setTime(rs.getString("time"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}

		return submission;
	}

    public Submission getSubmissionFromUserIdProblemIdAttempts(String userId, String problemId, String attempt) {

        if (logger.isDebugEnabled()) {
            logger.debug("Obtendo submission para o usuario: " + userId + " problemId: " + problemId + " attempt: "+ attempt +"...");
        }
        ResultSet rs = null;
        PreparedStatement statement = null;

        Submission submission = new Submission();

        try {

            if (logger.isDebugEnabled()) {
                logger.debug(SELECT_SUBMISSION);
            }

            statement = Connector.getConnection().prepareStatement(
                    GET_FROM_USER_PROBLEM_ATTEMPTS);
            statement.setString(1, userId);
            statement.setString(2, problemId);
            statement.setString(3, attempt);
            rs = statement.executeQuery();

            if (rs.next()) {
                submission.setId(rs.getLong("id"));
                submission.setVersion(rs.getLong("version"));
                submission.setProblemId(rs.getLong("problem_id"));
                submission.setSubmission(rs.getString("submission"));
                submission.setEvaluation(rs.getString("evaluation"));
                submission
                        .setSubmissionDate(rs.getTimestamp("submission_date"));
                submission.setDetailedLog(rs.getBoolean("detailed_log"));
                submission.setDiffFile(rs.getString("diff_file"));
                submission.setLinguageId(rs.getLong("language_id"));
                submission.setTries(rs.getInt("tries"));
                submission.setOutput(rs.getString("output"));
                submission.setUserId(rs.getLong("user_id"));
                submission.setTime(rs.getString("time"));
                submission.setPlagiumStatus(rs.getInt("plagium_status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourcesUtil.closeResultSet(rs);
            ResourcesUtil.closeStatement(statement);
        }

        return submission;
    }

    public List<String> getSubmissionPathByCluster(String languageId, String problemId, String institutionId,String year, String similarity) {

        if (logger.isDebugEnabled()) {
            logger.debug("Obtendo lista de submissões para plágio para a linguagem: " + languageId + " problemId: " + problemId + " institution: "+ institutionId +" timeLimit: " + year + " similarity > " + similarity);
        }
        ResultSet rs = null;
        PreparedStatement statement = null;

        ArrayList<String> submissionPathList = new ArrayList<String>();

        try {

            if (logger.isDebugEnabled()) {
                logger.debug(GET_DIR_LIST_BY_CLUSTER);
            }

            statement = Connector.getConnection().prepareStatement(
                    GET_DIR_LIST_BY_CLUSTER);
            statement.setString(1, problemId);
            statement.setString(2, languageId);
            statement.setString(3, institutionId);
            statement.setString(4, similarity);
            statement.setString(5, similarity);
            statement.setString(6, year);



            rs = statement.executeQuery();

            while(rs.next()) {
                submissionPathList.add(rs.getString("path"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourcesUtil.closeResultSet(rs);
            ResourcesUtil.closeStatement(statement);
        }

        return submissionPathList;
    }

	public void updateSubmission(Submission submission) {

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando submission " + submission.getId() + "...");
		}
		PreparedStatement statement = null;
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_SUBMISSION);
			}

			statement = Connector.getConnection().prepareStatement(
					UPDATE_SUBMISSION);

			statement.setString(1, submission.getEvaluation());
			statement.setString(2, submission.getTime());

			statement.setLong(3, submission.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourcesUtil.closeStatement(statement);
		}
	}

    public void markAllAsNotMatched() {

        if (logger.isDebugEnabled()) {
            logger.debug("Marcando todas submissões como não plágio ...");
        }
        PreparedStatement statement = null;
        try {

            if (logger.isDebugEnabled()) {
                logger.debug(UPDATE_SUBMISSION);
            }

            statement = Connector.getConnection().prepareStatement(
                    MARK_ALL_NOT_MATCHED);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourcesUtil.closeStatement(statement);
        }
    }

    public void updatePlagiumStatus(Submission submission) {

        if (!( (submission.getPlagiumStatus()==Submission.PLAGIUM_STATUS_TEACHER_CLEAN) || (submission.getPlagiumStatus()==Submission.PLAGIUM_STATUS_TEACHER_PLAGIUM) || (submission.getPlagiumStatus()==Submission.PLAGIUM_STATUS_MATCHED))){
            submission.setPlagiumStatus(Submission.PLAGIUM_STATUS_MATCHED);
        if (logger.isDebugEnabled()) {
            logger.debug("Atualizando plagium status da submission " + submission.getId() + "...");
        }
        PreparedStatement statement = null;
        try {

            if (logger.isDebugEnabled()) {
                logger.debug(UPDATE_SUBMISSION);
            }

            statement = Connector.getConnection().prepareStatement(
                    UPDATE_PLAGIUM_STATUS);

            statement.setLong(1, submission.getPlagiumStatus());
            statement.setLong(2, submission.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourcesUtil.closeStatement(statement);
        }
            UserProblemDao userProblemDao = new UserProblemDaoMySQL();
            userProblemDao.updateUserProblemSimilarity(submission.getUserId(),submission.getProblemId(),UserProblem.SIMILARITY_STATUS_MATCHED);
//            UserProblem.findByUserAndProblem(user,problem).updateSimilarityStatusBySystem(UserProblem.SIMILARITY_STATUS_MATCHED);
    }
    }
}
