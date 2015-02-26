package com.siit.lab2.core;

/**
 * Created by kirill-good on 26.2.15.
 */
public class HotDog {
    public final int x,y;

    public HotDog(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean found(int x,int y){
        return (this.x == x) && (this.y == y);
    }

}
