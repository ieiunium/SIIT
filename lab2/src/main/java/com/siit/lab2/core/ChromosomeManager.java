package com.siit.lab2.core;


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
    private Chromosome chromosomes[] = null;
    private final int numberOfDogs;
    public ChromosomeManager(int numberOfDogs, int gensPerDog){
        if(numberOfDogs < 1){
            this.numberOfDogs = 50;
        }else {
            this.numberOfDogs = numberOfDogs;
        }
        chromosomes = new Chromosome[numberOfDogs];
        for(int i = 0;i<numberOfDogs;i++){
            chromosomes[i]= new Chromosome(gensPerDog);
        }
    }
    public List<Chromosome> evolution(int steps,World world){
        PrintWriter pwStep;
        PrintWriter pwBest;
        PrintWriter pwStepBest;
        try {
            pwStep = new PrintWriter(new File("step.txt"));
            pwBest = new PrintWriter(new File("best.txt"));
            pwStepBest = new PrintWriter(new File("stepbest.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("cant open txt files");
        }
        List<Chromosome> resList = new ArrayList<Chromosome>();
        Chromosome children[] = chromosomes.clone();
        int rouletteWheel[] = new int[chromosomes.length];

        for(int step = 0;step < steps; step++) {
            Chromosome reserv = new Chromosome(1000);
            for(Chromosome i:chromosomes){
                i.calcFitness(world);
                if(i.getFitness()<0){
                    i.reset(reserv);
                    i.calcFitness(world);
                }else{
                    reserv = i.getCopy();
                }

            }
            Arrays.sort(chromosomes);
            if(step%50==0){
                resList.add(chromosomes[0].getCopy());
            }
            System.out.println(step + "\t" + chromosomes[0].getFitness());
            pwStepBest.println(step + "\t" + chromosomes[0].getFitness());
            pwStep.println(step);
            pwBest.println(chromosomes[0].getFitness());
            int sum = 0;
            int j = 0;
            for(Chromosome i:chromosomes){
                int tmp=0;
                if(i.getFitness()==0) {
                    pwBest.flush();
                    pwBest.close();
                    pwStep.flush();
                    pwStep.close();
                    pwStepBest.flush();
                    pwStepBest.close();
                    resList.add(i.getCopy());
                    return resList;
                }else{
                    //tmp = (i.getFitness()>1000)?0:(1000 - i.getFitness());
                    tmp = 100000/i.getFitness();
                }
                if(tmp>0){
                    sum += tmp;
                }
                rouletteWheel[j] = sum;

                j++;
            }
            for (int i = 0; i < chromosomes.length; i++) {
                int i1 = getRandomIndex(sum,rouletteWheel)/2;
                int i2;
                do{
                    i2 = getRandomIndex(sum,rouletteWheel)/2;
                }while (i1==i2);
                children[i] = Chromosome.crossOver(chromosomes[i1], chromosomes[i2]);
            }
            Chromosome tmp[] = children;
            children = chromosomes;
            chromosomes = tmp;
        }
        pwBest.flush();
        pwBest.close();
        pwStep.flush();
        pwStep.close();
        pwStepBest.flush();
        pwStepBest.close();
        return resList;
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
