package com.siit.lab2.swing;

import com.siit.lab2.core.Chromosome;
import com.siit.lab2.core.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by kirill-good on 13.2.15.
 */
public class Plotter extends JFrame{
    private List<Chromosome> chromosomes;
    private World world;
    int DX=25,DY=25;
    public Plotter(List<Chromosome> chromosomes, World world){
        this.chromosomes = chromosomes;
        this.world = world;
        this.setSize(2*DX+world.width,2*DY+world.heigth);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        world.paint(g,DX,DY);
        for(Chromosome i:chromosomes){
            world.drawPath(i, g,DX,DY);
        }
    }
}
