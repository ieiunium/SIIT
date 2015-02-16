package com.siit.lab1.core;

import java.util.Random;

/**
 * Created by kirill-good on 9.2.15.
 */
public class Dog implements Comparable{
    private boolean gens[];
    private int numberOfGens;
    public static final Random random = new Random();
    public Dog(final int numberOfGens){
        if(numberOfGens < 1){
            this.numberOfGens = 1000;
        }else {
            this.numberOfGens = numberOfGens;
        }
        gens = new boolean[numberOfGens];
        for(int i=0;i<gens.length;i++){
            gens[i] = random.nextBoolean();
        }
    }
    private Dog(){

    }
    public int fitness(){
        int res=0;
        for(boolean i:gens){
            if(i){
                res++;
            }
        }
        return res;
    }
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(boolean i:gens){
            stringBuilder.append(i?1:0);
        }
        return stringBuilder.toString();
    }
    public Dog getCopy(){
        Dog dog = new Dog();
        dog.gens = this.gens.clone();
        dog.numberOfGens = this.numberOfGens;
        return dog;
    }
    public static void crossOver(Dog mother,Dog father){
        if(mother.numberOfGens!=father.numberOfGens){
            return;
        }
        final int border = random.nextInt(mother.numberOfGens);
        for(int i = 0; i < border; i++){
            boolean tmp = father.gens[i];
            father.gens[i] = mother.gens[i];
            mother.gens[i] = tmp;
        }
        for(int i = 0; i < mother.gens.length; i++){
            if(random.nextInt(1000)<1){
                father.gens[i] = !father.gens[i];
            }
            if(random.nextInt(1000)<1){
                mother.gens[i] = !mother.gens[i];
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Dog){
            return ((Dog)o).fitness() - this.fitness();
        }else {
            return 0;
        }
    }
}
