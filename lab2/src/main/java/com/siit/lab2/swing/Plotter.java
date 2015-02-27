package com.siit.lab2.swing;

import com.siit.lab2.core.Chromosome;
import com.siit.lab2.core.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by kirill-good on 13.2.15.
 */
public class Plotter extends JFrame{
    private List<Chromosome> chromosomes;
    private World world;
    int DX=25,DY=25;
    public Plotter(List<Chromosome> chromosomes, World world,String filename){
        this.chromosomes = chromosomes;
        this.world = world;
        this.setSize(2*DX+world.width,2*DY+world.heigth);
        this.saveToPNG(filename);
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

    public void saveToPNG(String fileName){
        BufferedImage image = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
        try {
            Graphics g = image.getGraphics();
            paint(g);
            ImageIO.write(image, "png", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
