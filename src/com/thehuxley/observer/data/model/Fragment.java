package com.thehuxley.observer.data.model;

/** 
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public class Fragment {
	
	private long id;
	private long version;
    private long numberOfLines;
    private long plagiumId;
    private long fragmentsIdx;
    private long startLine1;
    private long startLine2;
    private double percentage;

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

    public long getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(long numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public long getPlagiumId() {
        return plagiumId;
    }

    public void setPlagiumId(long plagiumId) {
        this.plagiumId = plagiumId;
    }

    public long getFragmentsIdx() {
        return fragmentsIdx;
    }

    public void setFragmentsIdx(long fragmentsIdx) {
        this.fragmentsIdx = fragmentsIdx;
    }

    public long getStartLine1() {
        return startLine1;
    }

    public void setStartLine1(long startLine1) {
        this.startLine1 = startLine1;
    }

    public long getStartLine2() {
        return startLine2;
    }

    public void setStartLine2(long startLine2) {
        this.startLine2 = startLine2;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
