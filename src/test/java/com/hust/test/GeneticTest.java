package com.hust.test;

import org.junit.Before;
import org.junit.Test;

import com.hust.optimize.algorithm.Genetic;

public class GeneticTest {
    private int[] inputs;

    @Before
    public void init() {
        inputs = new int[] { 2, 4, 5, 3, 7, 5, 9, 1, 8, 5, 6, 3 };
    }
    
    @Test
    public void genetic(){
        Genetic gene = new Genetic();
        gene.setInputs(inputs);
        gene.setInitialNumber(50);
        gene.setMaxGeneration(50);
        gene.setTargetValue(3);
        gene.processing();
    }
}
