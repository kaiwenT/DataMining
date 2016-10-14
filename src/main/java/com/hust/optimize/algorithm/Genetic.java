package com.hust.optimize.algorithm;

import java.awt.SecondaryLoop;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ServiceConfigurationError;

import com.hust.optimize.Data;
import com.hust.util.CommonUtils;

public class Genetic {
    private int generationSum;
    private int maxGeneration;
    private List<List<Data>> initGroup;
    private LinkedList<List<Data>> generations;
    private List<Float> values;
    private int[] inputs;
    private float targetValue;

    public int getGenerationSum() {
        return generationSum;
    }

    public void setGenerationSum(int generationSum) {
        this.generationSum = generationSum;
    }

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

    private void variation() {

    }

    private void mating() {

        int maleIndex = -1, femaleIndex = -1;
        Random ran = new Random();
        // 随机选两条染色体（数据）
        while (maleIndex == femaleIndex || maleIndex == -1 || femaleIndex == -1) {
            maleIndex = ran.nextInt(generations.size());
            femaleIndex = ran.nextInt(generations.size());
        }
        List<Data> male = generations.get(maleIndex);
        List<Data> female = generations.get(femaleIndex);

        // 随机两个基因交换位置
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
        while (iterator.hasNext()) {
            List<Data> weight = iterator.next();
            if (weight == null || weight.size() != inputs.length || weight.size() == 0) {
                throw new RuntimeException();
            }
            for (int i = 0; i < weight.size(); i++) {
                values.add(weight.get(i).getValue() * inputs[i]);
            }
        }
    }

    private void select() {
        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i) < targetValue) {
                generations.remove(i);
            }
        }
    }

    public List<Float> getValues() {
        return values;
    }

    public void setValues(List<Float> values) {
        this.values = values;
    }

    public List<List<Data>> getInitGroup() {
        return initGroup;
    }

    public void setInitGroup(List<List<Data>> initGroup) {
        this.initGroup = initGroup;
    }

    public LinkedList<List<Data>> getGenerations() {
        return generations;
    }

    public void setGenerations(LinkedList<List<Data>> generations) {
        this.generations = generations;
    }

}
