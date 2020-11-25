package com.thehuxley.observer.data.model;


/**
 * @author Romero B. de S. Malaquias
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class QuestionnaireShiroUser {

	private long id;
	private long version;
	private long questionnaireId;
	private long userId;
	private double score;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
