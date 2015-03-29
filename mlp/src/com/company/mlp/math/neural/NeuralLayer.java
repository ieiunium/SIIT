package com.company.mlp.math.neural;

import com.company.mlp.math.neural.functions.ActivationFunction;

/**
 * Created by kirill-good on 10.2.15.
 */
public class NeuralLayer {
    protected Neuron neuron[];
    protected double outs[];

    public NeuralLayer(int in,int out,ActivationFunction activationFunction){
        neuron = new Neuron[out];
        outs = new double[out];
        for(int i = 0;i<neuron.length;i++){
            neuron[i] = new Neuron(in,activationFunction);
            outs[i] = 0;
        }
    }
    double[] getOut(double x[]){
        for(int i = 0;i<neuron.length;i++){
            outs[i] = neuron[i].getOut(x);
        }
        return outs;
    }
}
