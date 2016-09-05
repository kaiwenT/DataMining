package com.hust.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestBase {

    private void handleArray(String[] array) {
        array[0] = "changed";
    }

    // @Test
    public void test() {
        String[] str = { "1", "3", "4" };
        handleArray(str);
        System.out.println(str[0]);

        List<String> list = new ArrayList<String>();
        handleList(list);
        System.out.println(list.get(1));
    }

    private void handleList(List<String> list) {
        List<String> tmp = list;
        tmp.add("new");
        tmp.add("new 2");
    }
    
    
}
