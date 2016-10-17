package com.hust.optimize.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.hust.optimize.Data;
import com.hust.util.CommonUtils;

public class Genetic {

    private int maxGeneration;
    private int initialNumber;
    private LinkedList<List<Data>> generations;
    private List<Float> values;
    private int[] inputs;
    private float targetValue;
    private List<Data> OptimalVector;

    public int getMaxGeneration() {
        return maxGeneration;
    }

    public void setMaxGeneration(int maxGeneration) {
        this.maxGeneration = maxGeneration;
    }

    public int[] getInputs() {
        return inputs;
    }

    public void setInputs(int[] inputs) {
        this.inputs = inputs;
    }

    public float getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(float targetValue) {
        this.targetValue = targetValue;
    }

    public List<Float> getValues() {
        return values;
    }

    public void setValues(List<Float> values) {
        this.values = values;
    }

    public LinkedList<List<Data>> getGenerations() {
        return generations;
    }

    public void setGenerations(LinkedList<List<Data>> generations) {
        this.generations = generations;
    }

    private void variation() {
        Random ran = new Random();
        if (generations.size() < 3) {
            return;
        }
        int varnum = ran.nextInt(generations.size());
        List<Integer> varlist = new ArrayList<Integer>();
        for (int i = 0; i < varnum; i++) {
            int num = ran.nextInt(generations.size());
            if (varlist.contains(num)) {
                i--;
                continue;
            }
            varlist.add(num);
            List<Data> vector = generations.get(num);
            List<Integer> varpoints = CommonUtils.generateRandomIntList(vector.size(), ran.nextInt(vector.size()));
            for (int index : varpoints) {
                Data data = vector.get(index);
                data.setValue(ran.nextFloat());
                vector.set(index, data);
            }
        }

    }

    private void mating() {
        Random ran = new Random();
        int mlnum = generations.size();
        for (int i = 0; i < mlnum; i++) {
            int maleIndex = -1, femaleIndex = -1;
            // 随机选两条染色体（数据）
            while (maleIndex == femaleIndex || maleIndex == -1 || femaleIndex == -1) {
                maleIndex = ran.nextInt(generations.size());
                femaleIndex = ran.nextInt(generations.size());
            }
            List<Data> male = generations.get(maleIndex);
            List<Data> female = generations.get(femaleIndex);

            // 随机产生两个基因交换位置
            int firstPosition = -1, secondPosition = -1;
            while (firstPosition == secondPosition || firstPosition == -1 || secondPosition == -1) {
                firstPosition = ran.nextInt(male.size());
                secondPosition = ran.nextInt(male.size());
            }
            // 如果第一个位置大于第二个位置，交换他们
            if (firstPosition > secondPosition) {
                int trans = firstPosition;
                firstPosition = secondPosition;
                secondPosition = trans;
            }

            List<Data> child1 = new ArrayList<Data>(male);
            List<Data> child2 = new ArrayList<Data>(female);
            // 交换基因片段
            for (int pos = firstPosition; pos <= secondPosition; pos++) {
                child1.set(pos, female.get(pos));
                child2.set(pos, male.get(pos));
            }
            resolveConflicts(child1, child2, firstPosition, secondPosition);
            generations.add(child1);
            generations.add(child2);
        }
    }

    private void flush() {
        LinkedList<List<Data>> tmpgene = new LinkedList<List<Data>>();
        for (List<Data> vector : generations) {
            if (!tmpgene.contains(vector)) {
                tmpgene.add(vector);
            }
        }
        generations = tmpgene;
    }

    private void resolveConflicts(List<Data> child1, List<Data> child2, int firstPos, int secondPos) {
        if (CommonUtils.hasEmpty(child1, child2) || CommonUtils.lowerThan0(firstPos, secondPos)) {
            throw new IllegalArgumentException();
        }
        for (int pos = firstPos; pos <= secondPos; pos++) {
            Data ele = child1.get(pos);
            int index;
            if ((index = child1.indexOf(ele)) < firstPos) {
                child1.set(index, child2.get(index));
            } else if ((index = child1.lastIndexOf(ele)) > secondPos) {
                child1.set(index, child2.get(index));
            }

            ele = child2.get(pos);
            if ((index = child2.indexOf(ele)) < firstPos) {
                child2.set(index, child1.get(index));
            } else if ((index = child2.lastIndexOf(ele)) > secondPos) {
                child2.set(index, child1.get(index));
            }

        }
    }

    private void evaluate() {
        values.clear();
        Iterator<List<Data>> iterator = generations.iterator();
        System.out.println("\n\nprocessing generation:");
        while (iterator.hasNext()) {
            List<Data> weight = iterator.next();
            printVector(weight);
            if (weight == null || weight.size() != inputs.length || weight.size() == 0) {
                throw new RuntimeException();
            }
            float sum = 0;
            for (int i = 0; i < weight.size(); i++) {
                sum += weight.get(i).getValue() * inputs[i];
            }
            values.add(Math.abs(sum - targetValue));
        }
    }

    private boolean select() {
        if (generations.size() == 1) {
            OptimalVector = generations.get(0);
            return true;
        }
        int deletenum = values.size() / 2;
        for (int i = 0; i < deletenum; i++) {
            float max = Float.MIN_VALUE;
            int index = -1;
            for (int j = 0; j < values.size(); j++) {
                if (values.get(j) == 0) {
                    OptimalVector = generations.get(j);
                    return true;
                }
                if (values.get(j) > max) {
                    max = values.get(j);
                    index = j;
                }
            }
            if (index != -1) {
                values.remove(index);
                generations.remove(index);
            }
        }
        return false;
    }

    private void init() {
        generations = new LinkedList<List<Data>>();
        values = new ArrayList<Float>();
        Random ran = new Random();
        for (int i = 0; i < initialNumber; i++) {
            List<Data> vector = new ArrayList<Data>();
            for (int j = 0; j < inputs.length; j++) {
                Data data = new Data();
                data.setIndex(j);
                data.setValue(ran.nextFloat());
                vector.add(data);
            }
            generations.add(vector);
        }
    }

    synchronized private void printInit() {
        String input = "";
        for (int i = 0; i < inputs.length; i++) {
            input += inputs[i] + "\t";
        }
        System.out.println("inputs:" + input);
        System.out.println("maxGeneration:" + maxGeneration);
        System.out.println("Generations:");
        for (List<Data> list : generations) {
            printVector(list);
        }
    }

    private void printVector(List<Data> vector) {
        String vectorstr = "";
        for (Data data : vector) {
            vectorstr += data.getValue() + "\t";
        }
        float sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += vector.get(i).getValue() * inputs[i];
        }
        System.out.println("vector:" + vectorstr + "\t value:" + sum);
    }

    public void processing() {
        if (inputs == null || inputs.length == 0) {
            throw new IllegalArgumentException("input data cannot be empty and null");
        }
        if (maxGeneration == 0) {
            throw new IllegalArgumentException("maxGeneration must be bigger than 0");
        }
        init();
        if (generations == null || generations.size() == 0) {
            throw new IllegalArgumentException("initGroup cannot be empty and null");
        }
        printInit();
        List<Integer> varlist = CommonUtils.generateRandomIntList(maxGeneration, 0);
        for (int i = 0; i < maxGeneration; i++) {
            mating();
            // if (varlist.contains(i)) {
            // variation();
            // }
            flush();
            evaluate();
            if (select()) {
                System.out.println("OptimalVector:");
                printVector(OptimalVector);
                return;
            }
            System.out.println("generation number:" + generations.size());
            if (generations.size() <= 2) {
                break;
            }
        }
        OptimalVector = getOptimal();
        System.out.println("OptimalVector:");
        printVector(OptimalVector);
        // System.out.println("generations:");
        // for (List<Data> list : generations) {
        // printVector(list);
        // }
    }

    private List<Data> getOptimal() {
        evaluate();
        float min = Float.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) < min) {
                min = values.get(i);
                index = i;
            }
        }
        return generations.get(index);
    }

    public int getInitialNumber() {
        return initialNumber;
    }

    public void setInitialNumber(int initialNumber) {
        this.initialNumber = initialNumber;
    }

    public List<Data> getOptimalVector() {
        return OptimalVector;
    }

}
