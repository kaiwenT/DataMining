package com.hust.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestBase {

    private void handleArray(String[] array) {
        array[0] = "changed";
    }

    @Test
    public void test() {
        String[] str1 = { "1", "3", "4" };
        String[] str2 = { "1", "3", "4" };
        int[] data = { 1, 2, 3 };
        // handleArray(str);
        // System.out.println(str[0]);

        // List<String> list = new ArrayList<String>();
        // handleList(list);
//        testclass(data);
//        System.out.println( data[1] instanceof Integer);
        System.out.println(Arrays.equals(str1, str2));
    }

    private void testclass(float[] data){
//         System.out.println(data instanceof Integer);
    }
    private void handleList(List<String> list) {
        List<String> tmp = list;
        tmp.add("new");
        tmp.add("new 2");
    }

}
