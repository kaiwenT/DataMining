package com.hust.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hust.cluster.Canopy;
import com.hust.cluster.DBScan;
import com.hust.cluster.KMeans;
import com.hust.convertor.Convertor;
import com.hust.convertor.TFIDFConvertor;
import com.hust.distance.CosDistance;

public class ClusterTest {

    private List<String[]> segList;

    @Before
    public void init() {
        segList = new ArrayList<String[]>();
        String[] str1 = { "12岁", "女生", "宿舍", "表演", "上吊", "身亡", "室友", "错过", "2次", "施救", "机会" };
        String[] str2 = { "12岁", "男生", "宿舍", "表演", "上吊", "身亡", "室友", "错过", "2次", "施救", "机会" };
        String[] str3 = { "12岁", "男生", "家中", "表演", "上吊", "身亡", "室友", "错过", "2次", "施救", "机会" };
        String[] str4 = { "12岁", "男生", "家中", "上演", "上吊", "身亡", "室友", "错过", "2次", "施救", "机会" };
        String[] str5 = { "12岁", "男生", "家中", "上演", "自杀", "身亡", "室友", "错过", "2次", "施救", "机会" };
        String[] str6 = { "12岁", "男生", "家中", "上演", "自杀", "死亡", "家人", "错过", "2次", "施救", "机会" };
        String[] str7 = { "12岁", "男生", "家中", "上演", "自杀", "死亡", "家人", "失去", "2次", "施救", "机会" };
        String[] str8 = { "12岁", "男生", "家中", "上演", "自杀", "死亡", "家人", "失去", "2次", "补刀", "机会" };
//        String[] str2 = { "12岁", "女生", "宿舍", "内", "上吊", "室友", "以为", "玩笑", "错失", "施救", "机会" };
//        String[] str3 = { "12岁", "住校", "女生", "宿舍", "身亡" };
//        String[] str4 = { "陕西", "手机", "早报", "0402" };
//        String[] str5 = { "四川", "通报", "小学", "女生", "死亡", "事件", "系", "意外", "排除", "他", "杀" };
        segList.add(str1);
        segList.add(str2);
        segList.add(str3);
        segList.add(str4);
        segList.add(str5);
        segList.add(str6);
        segList.add(str7);
        segList.add(str8);
//        segList.add(str2);
//        segList.add(str2);
//        segList.add(str2);
//        segList.add(str2);
//        segList.add(str2);
//        segList.add(str3);
//        segList.add(str5);
//        segList.add(str5);
//        segList.add(str5);
//        segList.add(str5);
//        segList.add(str5);
//        segList.add(str5);
//        segList.add(str4);
//        segList.add(str3);
//        segList.add(str2);
//        segList.add(str1);
//        segList.add(str4);
//        segList.add(str4);
//        segList.add(str4);
//        segList.add(str4);
    }

//     @Test
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

    // @Test
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
    }
}
