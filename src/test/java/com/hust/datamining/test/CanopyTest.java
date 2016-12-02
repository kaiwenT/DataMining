package com.hust.datamining.test;

import java.util.List;

import org.junit.Test;

import com.hust.datamining.algorithm.cluster.Canopy;
import com.hust.datamining.convertor.Convertor;
import com.hust.datamining.convertor.TFIDFConvertor;

public class CanopyTest extends ClusterTest {
    @Test
    public void capony() {
        Convertor convertor = new TFIDFConvertor();
        convertor.setList(segList);
        List<double[]> vectors = convertor.getVector();
        Canopy canopy = new Canopy();
        canopy.setVectors(vectors);
        canopy.setThreshold(0.68f);
        try {
            canopy.clustering();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<List<Integer>> result = canopy.getResultIndex();
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
