/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import javax.swing.JFrame;

/**
 *
 * @author Leozin
 */
public class Start {
 
	public static JFrame window;
	
    public static void main(String[] args) {
        
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Cat Adventure");
        
        Quarto quarto = new Quarto();
        window.add(quarto);
        
        quarto.config.loadConfig();
        if(quarto.fullScreenOn == true) {
        	window.setUndecorated(true);
        }
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        quarto.setupGame();
        quarto.startGameThread();
    }
}
