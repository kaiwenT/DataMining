package com.hust.similarity;

import java.util.List;

import com.hust.util.VectorUtil;

public class CosSimilarity implements Similarity {

    @Override
    public double calculate(double[] vector1, double[] vector2) {
        if (null == vector1 || null == vector2 || vector1.length == 0 || vector2.length == 0) {
            return 0;
        }
        return VectorUtil.multiply(vector1, vector2) / (VectorUtil.module(vector1) * VectorUtil.module(vector2));
    }

    @Override
    public double calculate(double[] vector, List<double[]> list) {
        if (null == vector || null == list || vector.length == 0 || list.size() == 0) {
            return 0;
        }
        double sum = 0;
        for (double[] tmpVec : list) {
            sum += calculate(vector, tmpVec);
        }
        return sum / list.size();
    }

}
