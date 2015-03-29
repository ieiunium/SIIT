package com.company.mlp.math.neural;



import com.company.mlp.math.neural.functions.ActivationFunction;

import java.util.Random;

/**
 * Created by kirill-good on 10.2.15.
 */
public class Neuron{
    protected double T;
    protected double w[];
    protected ActivationFunction activationFunction;

    public Neuron(int n,ActivationFunction activationFunction){
        this.activationFunction = activationFunction;
        Random random = new Random();
        w = new double[n];
        T = (random.nextBoolean()?1:-1) * random.nextDouble();
        for(int i = 0;i<w.length;i++){
            w[i] = (random.nextBoolean()?1:-1) * random.nextDouble();
        }
    }
    double getOut(double x[]){
        double res = -T;
        for(int i = 0;i<w.length;i++){
            res += w[i]*x[i];
        }
        return activationFunction.F(res);
    }
}
