package com.siit.lab2.core;


import com.siit.lab2.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kirill-good on 9.2.15.
 */
public class ChromosomeManager {
    private Chromosome chromosomes[][];
    private Chromosome children[][];

    public ChromosomeManager(int numberOfDogs, int gensPerDog){
        chromosomes = new Chromosome[4][];
        children = new Chromosome[4][];

        for(int i = 0;i<chromosomes.length;i++){
            chromosomes[i]= new Chromosome[10];
            children[i] = new Chromosome[10];
            for(int j = 0;j<chromosomes[i].length;j++){
                chromosomes[i][j] = new Chromosome(gensPerDog);
                chromosomes[i][j].id = i;
            }
        }
    }
    public List<List<Chromosome>> evolution(int steps,World world){
        int distances[][] = new int[40][40];
        Chromosome all[] = new Chromosome[40];
        List<List<Chromosome>> resList = new ArrayList<List<Chromosome>>();
        boolean finded[] = {false,false,false,false};
        PrintWriter pwStep = null;
        PrintWriter pwFit = null;
        PrintWriter pwShFit = null;
        PrintWriter pwAll = null;
        PrintWriter pwX = null;
        PrintWriter pwY = null;
        PrintWriter pwF = null;
        PrintWriter pwSF = null;
        try {
            pwStep = new PrintWriter(new File("target/step.txt"));
            pwFit = new PrintWriter(new File("target/fitness.txt"));
            pwShFit = new PrintWriter(new File("target/sharingFitness.txt"));
            pwAll = new PrintWriter(new File("target/all.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int step = 0;step < steps; step++) {
            List<Chromosome> tmpRes = new ArrayList<Chromosome>();
            resList.add(tmpRes);

            int k=0;
            for(int i = 0;i<chromosomes.length;i++){
                for(int j = 0;j<chromosomes[i].length;j++) {
                    all[k++] = chromosomes[i][j];
                    chromosomes[i][j].calcFitness(world);
                    if (chromosomes[i][j].getFitness() == 0) {

                        finded[i] = true;
                    }
                }
            }

            for(int i = 0;i<all.length;i++ ){
                int S = 0;
                for(int j = i;j<all.length;j++ ){
                    distances[i][j] = Math.abs(all[i].getX() - all[j].getX()) + Math.abs(all[i].getY() - all[j].getY());
                    distances[j][i] = distances[i][j];
                }
            }

            double bF = 0;
            double bFit = 0;
            double avgF = 0;
            for(int i = 0;i<all.length;i++ ){
                double S = 0;
                for(int j = 0;j<all.length;j++ ){
                    S += Chromosome.s(distances[i][j]);
                }
                double f = 600-all[i].getFitness();
                double fit = (600-all[i].getFitness()) / (1+1*S);
                all[i].setSharingFitness(fit);
                //System.out.println(S);
                if(bFit<fit){
                    bFit = fit;
                    bF = f;
                }
                avgF += all[i].getSharingFitness();
            }
            avgF /=40;

            try {
                File f= new File("target/steps/"+String.valueOf(step));
                f.mkdirs();
                pwX = new PrintWriter(new File("target/steps/"+String.valueOf(step)+"/x.txt"));
                pwY = new PrintWriter(new File("target/steps/"+String.valueOf(step)+"/y.txt"));
                pwF = new PrintWriter(new File("target/steps/"+String.valueOf(step)+"/f.txt"));
                pwSF = new PrintWriter(new File("target/steps/"+String.valueOf(step)+"/sf.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for (int i=0;i<chromosomes.length;i++){
                Arrays.sort(chromosomes[i]);
                //System.out.print("["+ chromosomes[i][0].getFitness()+" "+ chromosomes[i][0].id + "] ");
                for(int j = 0;j<2;j++){
                    tmpRes.add(chromosomes[i][j]);
                    pwX.println(chromosomes[i][j].getX());
                    pwY.println(chromosomes[i][j].getY());
                    pwF.println(chromosomes[i][j].getFitness());
                    pwSF.println(chromosomes[i][j].getSharingFitness());
                }
            }
            pwX.flush();
            pwX.close();
            pwY.flush();
            pwY.close();
            pwF.flush();
            pwF.close();
            pwSF.flush();
            pwSF.close();

            for (int i = 0; i < chromosomes.length; i++) {
                if(!finded[i])
                    crossover(chromosomes[i],children[i]);
                else {
                    for(int j = 0;j<chromosomes[i].length;j++){
                        children[i][j] = chromosomes[i][j];
                    }
                }
            }

            System.out.println(step+" "+(600-bF)+" "+bFit);
            pwStep.println(step);
            pwFit.println(bF);
            pwShFit.println(bFit);


            Chromosome tmp[][] = chromosomes;
            chromosomes = children;
            children = tmp;
            if(finded[0]&&finded[1]&&finded[2]&&finded[3]){
                break;
            }
        }
        pwAll.flush();
        pwAll.close();
        pwStep.flush();
        pwStep.close();
        pwFit.flush();
        pwFit.close();
        pwShFit.flush();
        pwShFit.close();

        return resList;
    }
    public void crossover(Chromosome parents[],Chromosome children[]){
        for(int i = 0;i<parents.length;i++){
            int i1 = Chromosome.random.nextInt(parents.length)/2;
            int i2;
            do{
                i2 = Chromosome.random.nextInt(parents.length)/2;
            }while (i1==i2);
            children[i] = Chromosome.crossOver(parents[i1],parents[i2]);
            children[i].id = parents[i1].id;
        }
    }
    public int getRandomIndex(int sum,int rouletteWheel[]){
        if(sum<1){
            return Chromosome.random.nextInt(rouletteWheel.length);
        }
        int random = Chromosome.random.nextInt(sum);
        for(int i = 0; i<rouletteWheel.length; i++ ){
            if(random<rouletteWheel[i]){
                return i;
            }
        }
        return rouletteWheel.length-1;
    }
}
