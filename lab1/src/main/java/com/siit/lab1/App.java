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
        int maxSteps = Integer.MAX_VALUE;
        int dogs = 50;
        int gensPerDog = 1000;
        DogManager dogManager = new DogManager(dogs,gensPerDog);
        dogManager.evolution(maxSteps);
    }
}
