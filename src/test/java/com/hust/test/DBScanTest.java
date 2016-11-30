package com.hust.test;

import java.util.List;

import org.junit.Test;

import com.hust.datamining.cluster.DBScan;
import com.hust.datamining.convertor.Convertor;
import com.hust.datamining.convertor.TFIDFConvertor;
import com.hust.datamining.distance.CosDistance;

public class DBScanTest extends ClusterTest {

    @Test
    public void dbscan() {
        Convertor convertor = new TFIDFConvertor();
        convertor.setList(segList);
        List<double[]> vectors = convertor.getVector();
        DBScan dbscan = new DBScan();
        dbscan.setVectors(vectors);
        dbscan.setDis(new CosDistance(vectors));
        dbscan.setMinPts(2);
        dbscan.setEps(0.6);
        try {
            dbscan.clustering();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<List<Integer>> result = dbscan.getResultIndex();
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
        List<Integer> noise = dbscan.getNoisePts();
        System.out.println("noise");
        for (int index : noise) {
            String[] array = segList.get(index);
            for (String str : array) {
                System.out.print(str);
            }
            System.out.println();
        }
        System.out.println();
    }
}
