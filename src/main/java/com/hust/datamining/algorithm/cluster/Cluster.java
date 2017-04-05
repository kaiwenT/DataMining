package com.hust.datamining.algorithm.cluster;

import java.util.List;

import com.hust.datamining.simcal.Similarity;

public abstract class Cluster {
    protected List<double[]> vectors;
    protected Similarity simi;

    protected List<List<Integer>> resultIndex;

    public List<List<Integer>> getResultIndex() {
        return resultIndex;
    }

    public List<double[]> getVectors() {
        return vectors;
    }

    public void setVectors(List<double[]> vectors) {
        this.vectors = vectors;
    }

    public abstract void clustering() throws Exception;

    public Similarity getSimi() {
        return simi;
    }

    public void setSimi(Similarity simi) {
        this.simi = simi;
    }

}
