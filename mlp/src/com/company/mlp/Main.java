package com.company.mlp;

import com.company.mlp.math.genetics.ChromosomeManager;
import com.company.mlp.math.genetics.FitnessFunction;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.ActivationFunction;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static double ins[][] = new double[32][5];
    public static double outs[] = new double[32];
    static {
        for (int i = 0; i < 32; i++) {
            int k=i;
            int s = 0;
            for (int j = 0; j < 5; j++) {
                ins[i][j] = k%2;
                s += k%2;
                k /= 2;
            }
            if(s%2==0){
                outs[i] = +1;
            }else{
                outs[i] = -1;
            }
        }
    };

    public static void main(String[] args) {
        try {
            Runtime.getRuntime().exec("mkdir ./target");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int config[] = {5,5,1};
        NeuralNetwork nn = new NeuralNetwork(config, new ActivationFunction());
        System.out.println(nn.numOfGens());
        ChromosomeManager chromosomeManager = new ChromosomeManager(20,nn.numOfGens(),new FitnessFunction());
        chromosomeManager.evolution(100000);
    }
}
