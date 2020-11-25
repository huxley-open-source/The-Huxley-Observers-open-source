package com.thehuxley.observer.data.model;

import java.util.Date;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class Submission {
	
	private long id;
	private long version;
	private long problemId;
	private String submission;
	private String Evaluation;
	private Date submissionDate;
	private boolean detailedLog;
	private String diffFile;
	private long linguageId;
	private int tries;
	private String output;
	private long userId;
	private String time;
	private Problem problem;
	private Language language;
    private int plagiumStatus;

    public static final int PLAGIUM_STATUS_WAITING = 1;
    /**
     * O plágio já rodou e foi encontrado
     */
    public static final int PLAGIUM_STATUS_MATCHED = 2;
    /**
     * O plágio já rodou e não foi encontrado
     */
    public static final int PLAGIUM_STATUS_NOT_MATCHED = 3;
    /**
     * O plágio já rodou, indicou o plágio e o professor deliberadamente alterou o status da submissão indicando que NÃO é plágio
     */
    public static final int PLAGIUM_STATUS_TEACHER_CLEAN = 4;
    /**
     * O plágio já rodou, indicou o plágio e o professor deliberadamente alterou o status da submissão indicando que é um plágio.
     */
    public static final int PLAGIUM_STATUS_TEACHER_PLAGIUM = 5;



    public Submission() {
		this.problem = new Problem();
		this.language = new Language();
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getProblemId() {
		return problemId;
	}

	public void setProblemId(long problemId) {
		this.problemId = problemId;
	}

	public String getSubmission() {
		return submission;
	}

	public void setSubmission(String submission) {
		this.submission = submission;
	}

	public String getEvaluation() {
		return Evaluation;
	}

	public void setEvaluation(String evaluation) {
		Evaluation = evaluation;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public boolean isDetailedLog() {
		return detailedLog;
	}

	public void setDetailedLog(boolean detailedLog) {
		this.detailedLog = detailedLog;
	}

	public String getDiffFile() {
		return diffFile;
	}

	public void setDiffFile(String diffFile) {
		this.diffFile = diffFile;
	}

	public long getLinguageId() {
		return linguageId;
	}

	public void setLinguageId(long linguageId) {
		this.linguageId = linguageId;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return Long.toString(id);
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Language getLanguage() {
		return language;
	}

    public int getPlagiumStatus() {
        return plagiumStatus;
    }

    public void setPlagiumStatus(int plagiumStatus) {
        this.plagiumStatus = plagiumStatus;
    }
	
}
