package com.siit.lab1.core;


import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by kirill-good on 9.2.15.
 */
public class DogManager {
    private Dog dogs[] = null;
    private final int numberOfDogs;
    public DogManager(int numberOfDogs,int gensPerDog){
        if(numberOfDogs < 1){
            this.numberOfDogs = 50;
        }else {
            this.numberOfDogs = numberOfDogs;
        }
        dogs = new Dog[numberOfDogs];
        for(int i = 0;i<numberOfDogs;i++){
            dogs[i]= new Dog(gensPerDog);
        }
    }
    public void evolution(int steps){
        Dog children[] = dogs.clone();
        double lastAvgFitness=-1;
        int lastFitnessCount=0;

        for(int step = 0;step < steps && lastFitnessCount < 10;step++) {
            Arrays.sort(dogs);
            int half = numberOfDogs / 2;
            for (int i = 0; i < half; i++) {
                int i1 = Dog.random.nextInt(half);
                int i2;
                do{
                    i2 = Dog.random.nextInt(half);
                }while (i1==i2);
                Dog d1 = dogs[i1].getCopy();
                Dog d2 = dogs[i2].getCopy();
                Dog.crossOver(d1, d2);
                children[i * 2] = d1;
                children[i * 2 + 1] = d2;
            }
            Arrays.sort(children);
            Dog tmp[] = children;
            children = dogs;
            dogs = tmp;
            double avgFitness = 0;
            for (int i = 0; i < dogs.length; i++) {
                avgFitness+=dogs[i].fitness();
            }
            if( ((int)lastAvgFitness) == ((int)avgFitness) ){
                lastFitnessCount ++;
            }else{
                lastFitnessCount = 0;
            }
            lastAvgFitness = avgFitness;
            System.out.println(step+"\t"+avgFitness/dogs.length);
        }
    }
}
