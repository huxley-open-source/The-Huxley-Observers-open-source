package com.thehuxley.observer.data.model;

import java.util.ArrayList;

/**
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public class Plagium {
	
	private long id;
	private long version;
    private double percentage;
    private long submission1Id;
    private long submission2Id;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public long getSubmission1Id() {
        return submission1Id;
    }

    public void setSubmission1Id(long submission1Id) {
        this.submission1Id = submission1Id;
    }

    public long getSubmission2Id() {
        return submission2Id;
    }

    public void setSubmission2Id(long submission2Id) {
        this.submission2Id = submission2Id;
    }
    
    public void addToFragments(Fragment fragment){
        fragment.setFragmentsIdx(fragments.size());
        fragments.add(fragment);
    }

    public ArrayList<Fragment> getFragmentList(){
        return fragments;
    }
}
