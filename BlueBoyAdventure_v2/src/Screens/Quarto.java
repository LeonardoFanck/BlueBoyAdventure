    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Caracters.Entity;
import Caracters.Player;
import Config.AssetSetter;
import Config.ColissionChecker;
import Config.Config;
import Config.EventHandler;
import Config.KeyHandler;
import Config.Sound;
import Config.UI;
import ai.PathFinder;
import enviroment.EnvironmentManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

import tile.Map;
import tile.tileManager;
import tileInteractive.InteractiveTile;

/**
 *
 * @author Leozin
 */
public class Quarto extends JPanel implements Runnable{
    
    // configurações da tela
    final int tamanhoOriginalTile = 16;
    final int escala = 3;
    
    public final int tileSize = tamanhoOriginalTile * escala; //48x48
    public final int maxScreenColun = 20;
    public final int maxScreenRow = 12;
    public final int larguraTela = tileSize * maxScreenColun; // 960 pixels
    public final int alturaTela = tileSize * maxScreenRow; // 576 pixels
    
    // CONFIGURAÇÃO DO MUNDO
    public final int maxWorldCollum = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    
    // CONFIGURAÇÃO DE TELA CHEIA
    int screenWidth2 = larguraTela;
    int screenHeight2 = alturaTela;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    
    // FPS
    int FPS = 60;
    
    // SISTEMA
    public tileManager tileM = new tileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public ColissionChecker cChecker = new ColissionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    public Map map = new Map(this);
    Thread gameThread;
    
    // ENTIDADES E OBJETOS
    public Player player = new Player(this, keyH);  
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
//    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    
    // ESTADO DO JOGO
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;	
    public final int dialogueState = 3;
    public final int caracterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    
    public Quarto(){
        
        this.setPreferredSize(new Dimension(larguraTela, alturaTela));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); // É FOCADA PARA RECEBER O KEY INPUT
    }
    
    public void setupGame() {
    	
    	aSetter.setObject();
    	aSetter.setNPC();
    	aSetter.setMonster();	
    	aSetter.setInteractiveTile();
    	eManager.setup();
//    	 playMusic(0);
    	
    	gameState = titleState;
    	
    	tempScreen = new BufferedImage(larguraTela, alturaTela, BufferedImage.TYPE_INT_ARGB);
    	g2 = (Graphics2D)tempScreen.getGraphics();
    	
    	if(fullScreenOn == true) {
    		setFullScreen();
    	}
    }
    
    public void retry() {
    	
    	player.setDefaultPositions();
    	player.restoreLifeAndMana();
    	aSetter.setNPC();
    	aSetter.setMonster();
    }
    
    public void restart() {
    
    	player.setDefaultValues();
//    	player.setDefaultPositions(); NÃO NECESSÁRIO
//    	player.restoreLifeAndMana(); NÃO NECESSÁRIO
    	aSetter.setObject();
    	aSetter.setNPC();
    	aSetter.setMonster();
    	aSetter.setInteractiveTile();
    }
    
    public void setFullScreen() {
    	
    	// PEGANDO O MONITOR 
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
    	gd.setFullScreenWindow(Start.window);
    	
    	// PEGANDO OS DADOS DA TELA CHEIA (ALTURA E LARGURA)
    	screenWidth2 = Start.window.getWidth();
    	screenHeight2 = Start.window.getHeight();
    }
    
    public void startGameThread(){
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
    
    // METODO SLEEP DE FPS
    
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS; // 0,01666 segundos
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(gameThread != null){
            // 1 - ATUALIZAR: INFORMAÇÃO E POSIÇÃO DO PERSONAGEM
            update();
            
            // 2 -  DESENHAR: DESENHAR NA TELA A INFORMAÇÃO ATUALIZADA
            repaint(); // CHAMANDO A CLASE PAINTCOMPONENT - É PADRÃO JAVA
            
            try { 
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;
            } 
            catch (InterruptedException ex) {
                Logger.getLogger(Quarto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    */
    
    public void run(){
        
        double drawInterval = 1000000000/FPS; 
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;
        
        while(gameThread != null){
            
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if(delta >= 1){
                update();
                drawToTempScreen(); // DESENHA TUDO NO BUFFERED IMAGE
                drawToScreen(); // DESENHA O BUFFERED IMAGE NA TELA
                delta--;
                drawCount++;
            }
            
            if(timer >= 1000000000){
                //System.out.println("FPS:"+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void update(){
    	
    	if(gameState == playState) {
    		// JOGADOR
    		player.update();
    		
    		// NPC
    		for(int i = 0; i < npc[1].length; i++) {
    			if(npc[currentMap][i] != null) {
    				npc[currentMap][i].update();
    			}
    		}
    		
    		// MONSTER
    		for(int i = 0; i < monster[1].length; i++) {
    			if(monster[currentMap][i] != null) {
    				if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
    					monster[currentMap][i].update();
    				}
    				if(monster[currentMap][i].alive == false) {
    					monster[currentMap][i].checkDrop();
    					monster[currentMap][i] = null;
    				}
    			}
    		}
    		
    		// PROJÉTIL
    		for(int i = 0; i < projectile[1].length; i++) {
    			if(projectile[currentMap][i] != null) {
    				if(projectile[currentMap][i].alive == true) {
    					projectile[currentMap][i].update();
    				}
    				if(projectile[currentMap][i].alive == false) {
    					projectile[currentMap][i] = null;
    				}
    			}
    		}
    		
    		// PARTICULAS 
    		for(int i = 0; i < particleList.size(); i++) {
    			if(particleList.get(i) != null) {
    				if(particleList.get(i).alive == true) {
    					particleList.get(i).update();
    				}
    				if(particleList.get(i).alive == false) {
    					particleList.remove(i);
    				}
    			}
    		}
    		
    		// TILES INTERAGIVEIS
    		for(int i = 0; i < iTile[1].length; i++) {
    			if(iTile[currentMap][i] != null) {
    				iTile[currentMap][i].update();
    			}
    		}
    		eManager.update();
    	}
    	if(gameState == pauseState) {
    		 // nada
    	}
    }
    
    public void drawToTempScreen() {
    	
    	// DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
        	drawStart = System.nanoTime();
        }
        
        // TITLE SCREEN
        if(gameState == titleState) {
        	ui.draw(g2);
        }
        // MAP SCREEN
        else if(gameState == mapState) {
        	map.drawFullMapScreen(g2);
        }
        // OUTROS
        else {
        	
        	// TILE
            tileM.draw(g2);
            
            // TILE INTERACTIVES
            for(int i = 0; i < iTile[1].length; i++) {
            	if(iTile[currentMap][i] != null) {
            		iTile[currentMap][i].draw(g2);
            	}
            }
            
            // ADICIONA ENTIDADES À LISTA
            entityList.add(player);
            
            for(int i = 0; i < npc[1].length; i++) {
            	if(npc[currentMap][i] != null) {
            		entityList.add(npc[currentMap][i]); 
            	}
            }
            
            for(int i = 0; i < obj[1].length; i++) {
            	if(obj[currentMap][i] != null) {
            		entityList.add(obj[currentMap][i]);
            	}
            }
            for(int i = 0; i < monster[1].length; i++) {
            	if(monster[currentMap][i] != null) {
            		entityList.add(monster[currentMap][i]);
            	}
            }
            for(int i = 0; i < projectile[1].length; i++) {
            	if(projectile[currentMap][i] != null) {
            		entityList.add(projectile[currentMap][i]);
            	}
            }
            for(int i = 0; i < particleList.size(); i++) {
            	if(particleList.get(i) != null) {
            		entityList.add(particleList.get(i));
            	}
            }
            
            // ORDENANDO
            Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					
					int result = Integer.compare(e1.mundoY, e2.mundoY);
					return result;
				}
            	
            });
            
            // DESENHANDO AS ENTIDADES
            for(int i = 0; i < entityList.size(); i++) {
            	entityList.get(i).draw(g2);
            }
            
            // LIMPANDO A LISTA
            entityList.clear();
            
            // ENVIRONMENT
            eManager.draw(g2);
            
            // MINI MAP
            map.drawMiniMap(g2);
            
            // UI
            ui.draw(g2);
        }
        
        
        
        
        // DEBUG
        if(keyH.checkDrawTime) {
        	long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Tempo de renderização: " + passed, 10, 400);
            System.out.println("Tempo de renderização: " + passed);
        }
    }
    
    public void drawToScreen() {
    	
    	Graphics g = getGraphics();
    	g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
    	g.dispose();
    }
    
    // CONFIGURAÇÕES DE SOM
    public void playMusic(int i) {
    	
    	music.setFile(i);
    	music.play();
    	music.loop();
    }
    
    public void stopMusic() {
    	
    	music.stop();
    }
    
    public void playSE(int i) {
    	
    	se.setFile(i);
    	se.play();
    }
    
}
