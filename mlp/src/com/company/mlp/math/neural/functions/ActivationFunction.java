package com.company.mlp.math.neural.functions;

/**
 * Created by kirill-good on 10.2.15.
 */
public class ActivationFunction{
    public double F(double x){
        if(x>0){
            return +1;
        }else{
            return -1;
        }
    }
}
