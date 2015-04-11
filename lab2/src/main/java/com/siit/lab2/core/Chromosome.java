package com.siit.lab2.core;

import java.util.Random;

/**
 * Created by kirill-good on 11.2.15.
 */
public class Chromosome implements Comparable {
    protected double gens[];
    private FitnessFunction fitnessFunction = new FitnessFunction();
    private double fitnessValue;
    public static final Random random = new Random();
    public Chromosome(final int numberOfGens){
        gens = new double[numberOfGens];
        for(int i=0;i<gens.length;i++){
            gens[i] = random.nextBoolean()?-1:1 * random.nextDouble();
        }
    }

    public Chromosome(double[] gens) {
        this.gens = gens;
    }
    private Chromosome(){

    }
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("[");
        for(double i:gens){
            stringBuilder.append(i+", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public Chromosome getCopy(){
        Chromosome chromosome = new Chromosome();
        chromosome.gens = new double[this.gens.length];
        for (int i = 0;i<gens.length;i++){
            chromosome.gens[i] = this.gens[i];
        }
        chromosome.setFitnessFunction(this.fitnessFunction);
        return chromosome;
    }
    public static Chromosome crossOver(Chromosome mother,Chromosome father){

        double gens[] = new double[father.gens.length];
        for(int i = 0; i < father.gens.length; i++){
            if(random.nextBoolean()){
                gens[i] = father.gens[i];
                gens[i] = mother.gens[i];
            }
        }
        Chromosome res = new Chromosome(gens);
        res.setFitnessFunction(mother.fitnessFunction);
        return res;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Chromosome){
            if(((Chromosome)o).fitness() == this.fitness()){
                return 0;
            }else {
                return (((Chromosome) o).fitness() - this.fitness()) > 0 ? 1 : -1;
            }
        }else {
            return 0;
        }
    }

    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
    public void calcFitness(){
        fitnessValue = fitnessFunction.fitness(this);
    }
    public double fitness(){
        return fitnessValue;
    }

    public double[] getGens() {
        return gens;
    }

    public void setGens(double[] gens) {
        this.gens = gens;
    }

    public static double dist(Chromosome mother,Chromosome father){
        double s = 0;
        for(int i = 0 ; i< mother.gens.length;i++){
            s += Math.pow(mother.gens[i] - father.gens[i],2);
        }
        return Math.sqrt(s);
    }
    public void mutation(double p){
        for(int i = 0 ; i< gens.length;i++){
            if(random.nextDouble()<p){
                //gens[i] += random.nextDouble()*0.2 - 0.1;
                gens[i] = random.nextDouble()*2 - 1;
            }
        }
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }
}
