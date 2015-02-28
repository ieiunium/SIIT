package com.siit.lab2.core;

/**
 * Created by kirill-good on 26.2.15.
 */
public class Lake {
    public final int x1,x2,y1,y2;

    public Lake(int x1, int y1, int x2, int y2) {
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
    }

    public boolean drown(int x, int y){
        return (x1 <= x) && (x <= x2) && (y1 <= y ) && ( y <= y2);
    }
}
