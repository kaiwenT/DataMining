package com.hust.datamining.simcal;

import java.util.ArrayList;
import java.util.List;

public abstract class Similarity {
    private List<double[]> vectors;

    protected Similarity(List<double[]> vectors) {
        this.vectors = vectors;
    }

    public abstract double calculate(double[] vector1, double[] vector2);

    public double getResult(int index, List<Integer> set) {
        if (index < 0 || set == null || set.size() == 0) {
            return 0;
        }
        List<double[]> list = new ArrayList<double[]>();
        for (int i : set) {
            list.add(vectors.get(i));
        }
        return getTop1Result(vectors.get(index), list);
    }

    public double getResult(int index1, int index2) {
        if (index1 == index2) {
            return 1;
        }
        double[] vector1 = vectors.get(index1);
        double[] vector2 = vectors.get(index2);
        return calculate(vector1, vector2);
    }

    public double getAvgResult(double[] vector, List<double[]> list) {
        if (null == vector || null == list || vector.length == 0 || list.size() == 0) {
            return 0;
        }
        double sum = 0;
        for (double[] tmpVec : list) {
            sum += calculate(vector, tmpVec);
        }
        return sum / list.size();
    }

    public double getClosestResult(double[] vector, List<double[]> list) {
        if (null == vector || null == list || vector.length == 0 || list.size() == 0) {
            return 0;
        }
        double maxsim = 0;
        for (double[] elem : list) {
            double sim = calculate(elem, vector);
            if (maxsim < sim) {
                maxsim = sim;
            }
        }
        return maxsim;
    }

    public double getTop1Result(double[] vector, List<double[]> list) {
        if (null == vector || null == list || vector.length == 0 || list.size() == 0) {
            return 0;
        }
        return calculate(vector, list.get(0));
    }
}
