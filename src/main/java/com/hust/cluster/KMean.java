package com.hust.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMean extends Cluster {

    private int K;
    private List<double[]> centerVectors;
    private List<Integer> centerIndex;

    public int getK() {
        return K;
    }

    public void setK(int k) {
        K = k;
    }

    public List<double[]> getCenterVectors() {
        return centerVectors;
    }

    public List<Integer> getCenterIndex() {
        return centerIndex;
    }

    @Override
    public void clustering() {
        if (null == vectors) {
            return;
        }
        

    }

    private void selectRamdomCenter() {
        if (null == vectors || vectors.size() == 0) {
            return;
        }
        Random maker = new Random();
        for (int i = 0; i < vectors.size(); i++) {
            int random = maker.nextInt(K);
            if (!centerIndex.contains(random)) {
                centerIndex.add(random);
                centerVectors.add(vectors.get(random));
            } else {
                i--;
            }
        }
    }

}
