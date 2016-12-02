package com.hust.test;

import java.util.List;

import org.junit.Test;

import com.hust.datamining.algorithm.cluster.KMeans;
import com.hust.datamining.convertor.Convertor;
import com.hust.datamining.convertor.TFIDFConvertor;
import com.hust.datamining.distance.CosDistance;

public class KMeansTest extends ClusterTest {
    @Test
    public void kmeans() {
        Convertor convertor = new TFIDFConvertor();
        convertor.setList(segList);
        List<double[]> vectors = convertor.getVector();
        KMeans kmeans = new KMeans();
        kmeans.setVectors(vectors);
        kmeans.setIterationTimes(20);
        kmeans.setDis(new CosDistance(vectors));
        kmeans.setK(4);
        try {
            kmeans.clustering();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<List<Integer>> result = kmeans.getResultIndex();
        for (List<Integer> set : result) {
            for (int index : set) {
                String[] array = segList.get(index);
                for (String str : array) {
                    System.out.print(str);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
