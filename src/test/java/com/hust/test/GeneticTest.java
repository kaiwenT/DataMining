package com.hust.test;

import org.junit.Before;
import org.junit.Test;

import com.hust.optimize.algorithm.Genetic;

public class GeneticTest {
    private int[] inputs;

    @Before
    public void init() {
        inputs = new int[] { 2, 42, 5, 3, 71, 5, 39, -11000, 8, 5, 6, 3, 3, 5, 3, -60000, 2, 3, 1 };
    }

    @Test
    public void genetic() {
        Genetic gene = new Genetic();
        gene.setInput(inputs);
        gene.setInitialNumber(300);
        gene.setMaxGeneration(-1);
        gene.setTargetValue(1);
        gene.processing();
    }
}
