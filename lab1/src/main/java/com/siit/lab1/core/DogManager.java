package com.siit.lab1.core;


import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by kirill-good on 9.2.15.
 */
public class DogManager {
    private Dog dogs[] = null;
    private final int numberOfDogs;
    public DogManager(final int numberOfDogs){
        if(numberOfDogs < 1){
            this.numberOfDogs = 50;
        }else {
            this.numberOfDogs = numberOfDogs;
        }
        dogs = new Dog[numberOfDogs];
        for(int i = 0;i<numberOfDogs;i++){
            dogs[i]= new Dog(1000);
        }
    }
    public void evolution(){
        Dog children[] = dogs.clone();
        for(int step = 0;step < 10500;step++) {
            Arrays.sort(dogs);
            int half = numberOfDogs / 2;
            for (int i = 0; i < half; i++) {
                Dog d1 = dogs[i].getCopy();
                Dog d2 = dogs[i + 1].getCopy();
                Dog.crossOver(d1, d2);
                children[i * 2] = d1;
                children[i * 2 + 1] = d2;
            }
            Arrays.sort(children);
            Dog tmp[] = children;
            children = dogs;
            dogs = tmp;
        }
        for (int i = 0; i < dogs.length; i++) {
            System.out.println(dogs[i].fitness() + " " + children[i].fitness());
        }
    }
}
