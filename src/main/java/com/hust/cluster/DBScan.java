package com.hust.cluster;

import com.hust.distance.CosDistance;

public class DBScan extends Cluster {

    private int minPts;
    private double eps;

    public int getMinPts() {
        return minPts;
    }

    public void setMinPts(int minPts) {
        this.minPts = minPts;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    @Override
    public void clustering() throws Exception {
        // TODO Auto-generated method stub
        if (null == vectors || vectors.size() == 0) {
            throw new IllegalArgumentException("must init vectors before clustering");
        }
        if (null == dis) {
            dis = new CosDistance(vectors);
        }
        
    }

}
