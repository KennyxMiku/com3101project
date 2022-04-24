/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com3101project;

import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
/**
 *
 * @author kenny
 */
public class Com3101project {
     Scanner temp = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file=new File("energy.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip player = AudioSystem.getClip();
        player.open(audioStream);
        player.start();
        player.loop(Clip.LOOP_CONTINUOUSLY);
        View view = new View();
        Model model = new Model();
        Controller control = new Controller();
        model.setController(control);
        control.setModel(model);
        control.setView(view);
        view.setController(control);
        view.setModel(model);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               view.setVisible(true);
            }
        });
        model.newGame();
    }
    
}
