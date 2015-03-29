package com.siit.lab2.swing;

import com.siit.lab2.App;
import com.siit.lab2.core.Chromosome;
import com.siit.lab2.core.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
        //this.saveToPNG(filename);
        this.saveToFiles(filename);
    }
    public void saveToFiles(String fileName){
        fileName = App.folder+fileName + "folder";
        File f = new File(fileName);
        f.mkdir();
        for(int i = 0;i<chromosomes.size();i++){
            File tmpX = new File(f,String.valueOf(i)+"X");
            File tmpY = new File(f,String.valueOf(i)+"Y");
            try {
                PrintWriter pwX = new PrintWriter(tmpX);
                PrintWriter pwY = new PrintWriter(tmpY);
                world.printPath(chromosomes.get(i),pwX,pwY);
                pwX.flush();
                pwY.flush();
                pwX.close();
                pwY.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
            fileName = App.folder + fileName;
            ImageIO.write(image, "png", new File(fileName.matches("[.]png$")?fileName:fileName+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
