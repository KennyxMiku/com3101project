/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com3101project;
import java.beans.XMLEncoder;
import java.util.*;

/**
 *
 * @author kenny
 */
public class Com3101project {
     Scanner temp = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        
        
        
    }
    
}
