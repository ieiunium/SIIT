package com.siit.lab2.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kirill-good on 26.2.15.
 */
public class World {
    private List<Lake> lakes = new ArrayList<Lake>();
    private List<HotDog> hotDogs = new ArrayList<HotDog>();

    public final int width,heigth;
    public int []field[];

    public World(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        field = new int[width][];
        for(int i=0;i<width;i++){
            field[i] = new int[heigth];
        }

    }
    public void calcDistField(){
        for(int i=0;i<width;i++){
            for(int j=0;j<heigth;j++){
                field[i][j] = -1;
            }
        }
        class P{
            public int x,y;
            P(int x, int y) {
                this.y = y;
                this.x = x;
            }
        };
        Deque<P> points = new LinkedList<P>();
        P p = new P(hotDogs.get(0).x,hotDogs.get(0).y);
        points.addLast(p);

        field[p.x][p.y]=0;
        int offsetX[]={-1,0,1,1,1,0,-1,-1};
        int offsetY[]={1,1,1,0,-1,-1,-1,0};
        while(!points.isEmpty()){
            p = points.pollFirst();

            int i=field[p.x][p.y]+1;

            for(int j = 0;j<8;j++){
                int x = p.x + offsetX[j];
                int y = p.y + offsetY[j];
                if(x<0){
                    x+=width;
                    //continue;
                }
                if(y<0){
                    y+=heigth;
                    //continue;
                }
                if(x>=width){
                    x-=width;
                    //continue;
                }
                if(y>=heigth){
                    y-=heigth;
                    //continue;
                }
                if( ((field[x][y]==-1||field[x][y]>i)) && !this.drown(x,y)){
                    field[x][y] = i;
                    points.addLast(new P(x,y));
                }
            }
        }
        BufferedImage image = new BufferedImage(width,heigth,BufferedImage.TYPE_INT_ARGB);
        try {
            Graphics g = image.getGraphics();
            for(int i=0;i<heigth;i++){
                for(int j=0;j<width;j++){
                    g.setColor(Color.getHSBColor((field[j][i]/900.0F),1.0F,1.0F));
                    g.drawOval(j,i,1,1);
                }
            }
            this.paint(g,0,0);

            ImageIO.write(image, "png", new File("map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addLake(Lake lake){
        lakes.add(lake);
        //calcDistField();
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
                return -1;
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
        HotDog hd = null;
        for(HotDog i:hotDogs){
            int tmp = Math.abs(x - i.x)+Math.abs(y - i.y);
            if(tmp < res){
                res = tmp;
                hd = i;
            }
        }
        if(x>=400 && x<=605 && y>=400 && y<= 605){

            res += Math.abs(y-390)*2 + Math.abs(x - hd.x);
        }
        return res;
        //return this.field[x][y];
    }

    public void paint(Graphics g,int DX,int DY){
        g.setColor(Color.BLACK);
        g.drawRect(DX,DY,width,heigth);
        g.setColor(Color.RED);
        for(HotDog i:hotDogs){
            int r = 2;
            g.fillOval(DX + i.x-r,DY + i.y-r,r*2+1,r*2+1);
            //int R = 50;
            //g.drawOval(DX + i.x-R,DY + i.y-R,R*2+1,R*2+1);
        }
        g.setColor(Color.BLUE);
        for(Lake i:lakes){
            g.fillRect(i.x1+DX
                    ,i.y1+DY
                    ,i.x2-i.x1
                    ,i.y2-i.y1);
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

    public void printPath(Chromosome chromosome, PrintWriter pwX,PrintWriter pwY){
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
            pwX.println(x);
            pwY.println(y);
            if(found(x,y)){
                return;
            }
            if(drown(x, y)){
                return;
            }
        }
    }
}
