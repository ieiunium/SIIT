package com.company.mlp.math.genetics;

import com.company.mlp.Main;
import com.company.mlp.math.neural.NeuralNetwork;
import com.company.mlp.math.neural.functions.ActivationFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by kirill-good on 11.2.15.
 */
public class FitnessFunction {
    int config[] = {5,5,1};
    NeuralNetwork nn = new NeuralNetwork(config, new ActivationFunction());
    public void printTable(Chromosome chromosome,int id){
        File f = new File("./target/"+id);
        f.mkdir();
        PrintWriter pwIn = null;
        PrintWriter pwOut = null;
        PrintWriter pwMustBeOut = null;
        PrintWriter pwOk = null;
        try {
            pwIn = new PrintWriter(new File(f,"in.txt"));
            pwOut = new PrintWriter(new File(f,"out.txt"));
            pwMustBeOut = new PrintWriter(new File(f,"mustbeout.txt"));
            pwOk = new PrintWriter(new File(f,"ok.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        nn.setGens(chromosome.getGens());
        for (int i = 0; i < 32; i++) {
            double out1 = nn.getOut(Main.ins[i])[0];
            double out2 = Main.outs[i];
            for (int j = 0; j < Main.ins[i].length; j++) {
                pwIn.print((int)Main.ins[i][j]+" ");
            }
            pwIn.println();
            pwOut.println((int)out1);
            pwMustBeOut.println((int)out2);
            double d = Math.abs(out1 - out2);
            if (d < 0.1) {
                pwOk.println("+");
            } else {
                pwOk.println("-");
            }
        }
        pwIn.flush();
        pwIn.close();
        pwOut.flush();
        pwOut.close();
        pwMustBeOut.flush();
        pwMustBeOut.close();
        pwOk.flush();
        pwOk.close();
    }
    public double fitness(Chromosome chromosome) {
        double res = 0;
        nn.setGens(chromosome.getGens());
        for (int i = 0; i < 32; i++) {
            double out1 = nn.getOut(Main.ins[i])[0];
            double out2 = Main.outs[i];
            double d = Math.abs(out1 - out2);
            if (d < 0.1) {
                res += 1;
            } else {
            }
        }
        return res;
    }
}
