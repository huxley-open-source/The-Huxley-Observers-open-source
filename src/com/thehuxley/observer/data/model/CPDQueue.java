package com.thehuxley.observer.data.model;

/**
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class CPDQueue {
	
	private long id;
	private String language;
    private int problemId;
    private int institutionId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int id) {
        this.problemId = id;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }
}
