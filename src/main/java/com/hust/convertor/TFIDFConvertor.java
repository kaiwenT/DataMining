package com.hust.convertor;

import java.util.ArrayList;
import java.util.List;

public class TFIDFConvertor extends Convertor {

    @Override
    public List<double[]> getVector() {
        if (null == seglist) {
            return null;
        }
        List<double[]> list = new ArrayList<double[]>();
        for (String[] array : seglist) {
            double[] vector = new double[vectorBase.size()];
            for (String word : array) {
                double TF = (float) existAmountInArticle(word, array) / (float) array.length;
                double IDF = Math.log((float) seglist.size() / (float) (existAmmountInCorpus(word) + 1));
                vector[vectorBase.indexOf(word)] = TF * IDF;
            }
            list.add(vector);
        }
        return list;
    }

    /**
     * 计算语料库中，某个词出现的次数
     * 
     * @param word
     * @return
     */
    private long existAmmountInCorpus(String word) {
        if (null == word || word.length() == 0) {
            return 0;
        }
        long count = 0;
        for (String[] array : seglist) {
            for (String tmpword : array) {
                if (word.equals(tmpword)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * 计算某个词在一篇文章中出现的次数
     * 
     * @param word
     * @param words
     * @return
     */
    private long existAmountInArticle(String word, String[] words) {
        if (null == word || null == words || word.length() == 0 || words.length == 0) {
            return 0;
        }
        long count = 0;
        for (String tmpword : words) {
            if (word.equals(tmpword)) {
                count++;
            }
        }
        return count;
    }
}
