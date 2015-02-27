package com.siit.lab2.core;


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

        List<Chromosome> resList = new ArrayList<Chromosome>();
        Chromosome children[] = chromosomes.clone();
        int rouletteWheel[] = new int[chromosomes.length];

        for(int step = 0;step < steps; step++) {
            for(Chromosome i:chromosomes){
                i.calcFitness(world);
                if(resList.size()<50 && i.random.nextInt(1000)<500){
                    resList.add(i.getCopy());
                }
            }

            Arrays.sort(chromosomes);
            //System.out.println("\t"+chromosomes[0].getFitness());
            int sum = 0;
            int j = 0;
            for(Chromosome i:chromosomes){
                int tmp=0;
                if(i.getFitness()==0) {
                    //TODO
                    resList.add(i.getCopy());
                    return resList;
                }else{
                    //tmp = (i.getFitness()>1000)?0:(1000 - i.getFitness());
                    tmp = 100000/i.getFitness();
                }
                sum += tmp;
                rouletteWheel[j] = sum;

                j++;
            }

            double avgFitness = 0;
            for (int i = 0; i < chromosomes.length; i++) {
                avgFitness+= chromosomes[i].getFitness();
            }
            System.out.println(step+"\t"+avgFitness/ chromosomes.length);

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
        return resList;
    }

    public int getRandomIndex(int sum,int rouletteWheel[]){
        int random = Chromosome.random.nextInt(sum);
        for(int i = 0; i<rouletteWheel.length; i++ ){
            if(random<rouletteWheel[i]){
                return i;
            }
        }
        return rouletteWheel.length-1;
    }
}
