package com.siit.lab2.core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill-good on 26.2.15.
 */
public class World {
    private List<Lake> lakes = new ArrayList<Lake>();
    private List<HotDog> hotDogs = new ArrayList<HotDog>();

    public final int width,heigth;

    public World(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
    }
    public void addLake(Lake lake){
        lakes.add(lake);
    }
    public void addHotDog(HotDog hotDog){
        hotDogs.add(hotDog);
    }
    public int simulate(Chromosome chromosome,World world){
        int x0 = width / 2;
        int y0 = heigth / 2;
        int x = x0;
        int y = y0;
        boolean drowned=false;
        for(int i = 0; i<chromosome.gens.length; i++){
            switch (chromosome.gens[i]){
                case 0:
                    y += +1;
                    break;
                case 1:
                    y += -1;
                    break;
                case 2:
                    x += +1;
                    break;
                case 3:
                    x += -1;
                    break;
                default:
                    throw new RuntimeException("bad gene");
            }
            if(x<0){
                x+=width;
            }
            if(y<0){
                y+=heigth;
            }
            if(x>width){
                x-=width;
            }
            if(y>heigth){
                y-=heigth;
            }
            if(found(x,y)){
                return 0;
            }
            if(drown(x, y)){
                return Integer.MAX_VALUE/2;
            }
        }
        return dist(x,y);
    }
    public boolean found(int x,int y){
        for(HotDog i:hotDogs){
            if(i.found(x,y)){
                return true;
            }
        }
        return false;
    }
    public boolean drown(int x,int y){
        for(Lake i:lakes){
            if(i.drown(x, y)){
                return true;
            }
        }
        return false;
    }
    public int dist(int x,int y){
        int res = Integer.MAX_VALUE;
        for(HotDog i:hotDogs){
            int tmp = Math.abs(x - i.x)+Math.abs(y - i.y);
            if(tmp < res){
                res = tmp;
            }
        }
        return res;
    }

    public void paint(Graphics g,int DX,int DY){
        g.setColor(Color.BLACK);
        g.drawRect(DX,DY,width,heigth);
        g.setColor(Color.RED);
        for(HotDog i:hotDogs){
            int r = 2;
            g.fillOval(DX + i.x-r,DY + i.y-r,r*2+1,r*2+1);
            int R = 50;
            g.drawOval(DX + i.x-R,DY + i.y-R,R*2+1,R*2+1);
        }
        g.setColor(Color.BLUE);
        for(Lake i:lakes){
            g.fillRect(i.x1,i.y1,i.x2-i.x1,i.y2-i.y1);
        }

    }
    public void drawPath(Chromosome chromosome,Graphics g,int DX,int DY){
        int x0 = width / 2;
        int y0 = heigth / 2;
        int x = x0;
        int y = y0;
        boolean drowned=false;
        g.setColor(Color.getHSBColor(chromosome.random.nextFloat(),1.0F,1.0F));
        for(int i = 0; i<chromosome.gens.length; i++){
            switch (chromosome.gens[i]){
                case 0:
                    y += +1;
                    break;
                case 1:
                    y += -1;
                    break;
                case 2:
                    x += +1;
                    break;
                case 3:
                    x += -1;
                    break;
                default:
                    throw new RuntimeException("bad gene");
            }
            if(x<0){
                x+=width;
            }
            if(y<0){
                y+=heigth;
            }
            if(x>width){
                x-=width;
            }
            if(y>heigth){
                y-=heigth;
            }
            g.drawOval(x+DX,y+DY,1,1);
            if(found(x,y)){
                return;
            }
            if(drown(x, y)){
                return;
            }
        }
    }
}
