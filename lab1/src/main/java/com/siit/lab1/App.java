package com.siit.lab1;

import com.siit.lab1.core.Dog;
import com.siit.lab1.core.DogManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int maxSteps = 1500;
        int dogs = 1000;
        int gensPerDog = 1000;
        DogManager dogManager = new DogManager(dogs,gensPerDog);
        dogManager.evolution(maxSteps);
/*
        dogManager = new DogManager(dogs,gensPerDog);
        dogManager.evolution(steps);

        dogManager = new DogManager(dogs,gensPerDog);
        dogManager.evolution(steps);*/

    }
}
