package com.thehuxley.observer.data.model;



/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class QuestionnaireStatistics {
	
	
	private long id;
	private long version;
	private long questionnaireId;
	private long groupId;
	private double averageNote;
	private double standartDeviaton;
	private double greaterThenEqualsSeven;
	private double lessSeven;
	private double tryPercentage;
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
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public double getAverageNote() {
		return averageNote;
	}
	public void setAverageNote(double averageNote) {
		this.averageNote = averageNote;
	}
	public double getStandartDeviaton() {
		return standartDeviaton;
	}
	public void setStandartDeviaton(double standartDeviaton) {
		this.standartDeviaton = standartDeviaton;
	}
	public double getGreaterThenEqualsSeven() {
		return greaterThenEqualsSeven;
	}
	public void setGreaterThenEqualsSeven(double greaterThenEqualsSeven) {
		this.greaterThenEqualsSeven = greaterThenEqualsSeven;
	}
	public double getLessSeven() {
		return lessSeven;
	}
	public void setLessSeven(double lessSeven) {
		this.lessSeven = lessSeven;
	}
	public double getTryPercentage() {
		return tryPercentage;
	}
	public void setTryPercentage(double tryPercentage) {
		this.tryPercentage = tryPercentage;
	}
	
}
