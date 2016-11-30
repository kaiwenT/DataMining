package com.hust.datamining.cluster;

import java.util.List;

import com.hust.datamining.distance.Distance;

public abstract class Cluster {
    protected List<double[]> vectors;
    protected Distance dis;

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

    public Distance getDis() {
        return dis;
    }

    public void setDis(Distance dis) {
        this.dis = dis;
    }

}
