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
        DogManager dogManager = new DogManager(50);
        dogManager.evolution();
    }
}
