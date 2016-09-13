package com.hust.similarity;

import java.util.List;

public abstract class Similarity {

    /**
     * 计算两个向量的相似度
     * 
     * @param vector1
     * @param vector2
     * @return
     */
    public abstract double calculate(double[] vector1, double[] vector2);

    /**
     * 计算某个向量和一组向量的相似度的平均值
     * 
     * @param vector
     * @param list
     * @return
     */
    public abstract double calculate(double[] vector, List<double[]> list);
}
