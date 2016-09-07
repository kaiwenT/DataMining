package com.hust.cluster;

import java.util.List;

import com.hust.similarity.Similarity;

public abstract class Cluster {
    protected List<double[]> vectors;
    protected Similarity similarity;

    protected List<List<Integer>> resultIndex;
    protected List<List<double[]>> resultVector;

    public List<List<Integer>> getResultIndex() {
        return resultIndex;
    }

    public List<List<double[]>> getResultVector() {
        return resultVector;
    }

    public List<double[]> getVectors() {
        return vectors;
    }

    public void setVectors(List<double[]> vectors) {
        this.vectors = vectors;
    }

    public Similarity getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Similarity similarity) {
        this.similarity = similarity;
    }

    public abstract void clustering() throws Exception;
}
