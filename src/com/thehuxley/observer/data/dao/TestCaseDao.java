package com.thehuxley.observer.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.observer.data.Connector;
import com.thehuxley.observer.data.model.ResourcesUtil;
import com.thehuxley.observer.data.model.TestCase;

/**
 * @author Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public abstract class TestCaseDao {

	static Logger logger = LoggerFactory.getLogger(SubmissionDao.class);

	protected String LIST_BY_PROBLEM;
	protected long offset;
	protected long problemId;
	public static final long  LIMIT = 5;


	
	public void setProblemId(long problemId){
		this.problemId = problemId;
	}
	
	public ArrayList<TestCase> listTestCases(long offset) {

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo test case para o problem " + problemId + "...");
		}

		ArrayList<TestCase> testCases = new ArrayList<TestCase>();
		ResultSet rs = null;
		PreparedStatement statement = null;	
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(LIST_BY_PROBLEM);
			}

			statement = Connector.getConnection()
					.prepareStatement(LIST_BY_PROBLEM);
			statement.setLong(1, problemId);
			statement.setLong(2, offset);
			rs = statement.executeQuery();

			if (rs.next()) {
				TestCase testCase = new TestCase();
				testCase.setId(rs.getLong("id"));
				testCase.setProblemId(rs.getLong("problem_id"));
				testCase.setInput(rs.getString("input"));
				testCase.setOutput(rs.getString("output"));
				testCases.add(testCase);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ResourcesUtil.closeResultSet(rs);
			ResourcesUtil.closeStatement(statement);
		}		

		return testCases;
	}

}
