/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Screens.Quarto;

/**
 *
 * @author Leozin
 */
public class KeyHandler implements KeyListener{
    
	Quarto quarto;
	
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    
    public KeyHandler(Quarto quarto) {
    	this.quarto = quarto;
    }
    
    // DEBUG
    public boolean checkDrawTime = false;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        // TITLE STATE
        if(quarto.gameState == quarto.titleState) {
        	titleState(code);
        }
        
        // PLAY STATE
        else if(quarto.gameState == quarto.playState) {
        	playState(code);
        }
        
        // PAUSE STATE
        else if(quarto.gameState == quarto.pauseState) {
        	pauseState(code);
        }
        
        // DIALOGUE STATE
        else if(quarto.gameState == quarto.dialogueState) {
        	dialogueState(code);
        }
        
        // CARACTER STATE
        else if(quarto.gameState == quarto.caracterState) {
        	caracterState(code);
        }
        
        // OPTION STATE
        else if(quarto.gameState == quarto.optionState) {
        	optionState(code);
        }
        
        // GAME OVER STATE
        else if(quarto.gameState == quarto.gameOverState) {
        	gameOverState(code);
        }
        
        // TRADE STATE
        else if(quarto.gameState == quarto.tradeState) {
        	tradeState(code);
        }
    }
    
    public void titleState(int code) {
    	
    	if(quarto.ui.titleScreenState == 0) {
    		if(code == KeyEvent.VK_W){
                quarto.ui.commandNumber--;
                if(quarto.ui.commandNumber < 0) {
                	quarto.ui.commandNumber = 2;
                }
            }
            if(code == KeyEvent.VK_S){
                quarto.ui.commandNumber++;
                if(quarto.ui.commandNumber > 2) {
                	quarto.ui.commandNumber = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
            	if(quarto.ui.commandNumber == 0) {
            		quarto.ui.titleScreenState = 1;
            	}
            	if(quarto.ui.commandNumber == 1) {
            		// VAMOS ADICIONAR DEPOIS
            	}
            	if(quarto.ui.commandNumber == 2) {
            		System.exit(0);
            	}
            }
    	}
    	else if(quarto.ui.titleScreenState == 1) {
    		
    		if(code == KeyEvent.VK_W){
                quarto.ui.commandNumber--;
                if(quarto.ui.commandNumber < 0) {
                	quarto.ui.commandNumber = 3;
                }
            }
            if(code == KeyEvent.VK_S){
                quarto.ui.commandNumber++;
                if(quarto.ui.commandNumber > 3) {
                	quarto.ui.commandNumber = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
            	if(quarto.ui.commandNumber == 0) {
            		System.out.println("Caracteristicas da classe lutador");
            		quarto.gameState = quarto.playState;
            		quarto.playMusic(0);
            	}
            	if(quarto.ui.commandNumber == 1) {
            		System.out.println("Caracteristicas da classe ladrão");
            		quarto.gameState = quarto.playState;
            		quarto.playMusic(0);
            	}
            	if(quarto.ui.commandNumber == 2) {
            		System.out.println("Caracteristicas da classe mago");
            		quarto.gameState = quarto.playState;
            		quarto.playMusic(0);
            	}
            	if(quarto.ui.commandNumber == 3) {
            		quarto.ui.titleScreenState = 0;
            	}
            }
    	}
    }
    
    public void playState(int code) {
    	
    	if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P){
        	quarto.gameState = quarto.pauseState;
        }
        if(code == KeyEvent.VK_C) {
        	quarto.gameState = quarto.caracterState;
        }
        if(code == KeyEvent.VK_ENTER){
        	enterPressed = true;
        }
        if(code == KeyEvent.VK_F) {
        	shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
        	quarto.gameState = quarto.optionState;
        }
        
        // DEBUG
        if(code == KeyEvent.VK_T) {
        	if(checkDrawTime == false) {
        		checkDrawTime = true;
        	}
        	else if(checkDrawTime == true) {
        		checkDrawTime = false;
        	}
        }
        if(code == KeyEvent.VK_R) {
        	switch(quarto.currentMap) {
        	case 0: quarto.tileM.loadMap("/maps/worldV2.txt", 0); break;
        	case 1: quarto.tileM.loadMap("/maps/interior01.txt", 1); break;
        	}
        }
    }
    
    public void pauseState(int code) {
    	
    	if(code == KeyEvent.VK_P){
        	quarto.gameState = quarto.playState;
        }
    }
    
    public void dialogueState(int code) {
    	
    	if(code == KeyEvent.VK_ENTER) {
    		quarto.gameState = quarto.playState;
    	}
    }
    
    public void caracterState(int code) {
    	
    	if(code == KeyEvent.VK_C) {
    		quarto.gameState = quarto.playState;
    	}
    	if(code == KeyEvent.VK_ENTER) {
    		quarto.player.selectItem();
    	}
    	playerInventory(code);
    }

    public void optionState(int code) {
    	
    	if(code == KeyEvent.VK_ESCAPE) {
    		quarto.gameState = quarto.playState;
    	}
    	if(code == KeyEvent.VK_ENTER) {
    		enterPressed = true;
    	}
    	
    	int maxCommandNumber = 0;
    	switch(quarto.ui.subState) {
    	case 0: maxCommandNumber = 5; break;
    	case 3: maxCommandNumber = 1; break;
    	}
    	
    	if(code == KeyEvent.VK_W) {
    		quarto.ui.commandNumber--;
    		quarto.playSE(8);
    		if(quarto.ui.commandNumber < 0) {
    			quarto.ui.commandNumber = maxCommandNumber;
    		}
    	}
    	if(code == KeyEvent.VK_S) {
    		quarto.ui.commandNumber++;
    		quarto.playSE(8);
    		if(quarto.ui.commandNumber > maxCommandNumber) {
    			quarto.ui.commandNumber = 0;
    		}
    	}
    	if(code == KeyEvent.VK_A) {
    		if(quarto.ui.subState == 0) {
    			if(quarto.ui.commandNumber == 1 && quarto.music.volumeScale > 0) {
    				quarto.music.volumeScale--;
    				quarto.music.checkVolume();
    				quarto.playSE(8);
    			}
    			if(quarto.ui.commandNumber == 2 && quarto.se.volumeScale > 0) {
    				quarto.se.volumeScale--;
    				quarto.playSE(8);
    			}
    		}
    	}
    	if(code == KeyEvent.VK_D) {
    		if(quarto.ui.subState == 0) {
    			if(quarto.ui.commandNumber == 1 && quarto.music.volumeScale < 5) {
    				quarto.music.volumeScale++;
    				quarto.music.checkVolume();
    				quarto.playSE(8);
    			}
    			if(quarto.ui.commandNumber == 2 && quarto.se.volumeScale < 5) {
    				quarto.se.volumeScale++;
    				quarto.playSE(8);
    			}
    		}
    	}
    	
    	
    }

    public void gameOverState(int code) {
    	
    	// FAZENDO A ALTERAÇÃO DA SETA COM W
    	if(code == KeyEvent.VK_W) {
    		quarto.ui.commandNumber--;
    		 if(quarto.ui.commandNumber < 0) {
    			 quarto.ui.commandNumber = 1;
    		 }
    		 quarto.playSE(8);
    	}
    	// FAZENDO A ALTERAÇÃO DA SETA COM S
    	if(code == KeyEvent.VK_S) {
    		quarto.ui.commandNumber++;
    		if(quarto.ui.commandNumber > 1) {
    			quarto.ui.commandNumber = 0;
    		}
    		quarto.playSE(8);
    	}
    	// VERIFICANDO A POSIÇÃO ESCOLHIDA 
    	if(code == KeyEvent.VK_ENTER) {
    		if(quarto.ui.commandNumber == 0) {
    			quarto.gameState = quarto.playState;
    			quarto.retry();
    			quarto.playMusic(0);
    		}
    		else if(quarto.ui.commandNumber == 1) {
    			quarto.gameState = quarto.titleState;
    			quarto.ui.titleScreenState = 0;
    			quarto.restart();
    		}
    	}
    }
    
    public void tradeState(int code) {
    	
    	if(code == KeyEvent.VK_ENTER) {
    		enterPressed = true;
    	}
    	
    	if(quarto.ui.subState == 0) {
    		if(code == KeyEvent.VK_W) {
    			quarto.ui.commandNumber--;
    			if(quarto.ui.commandNumber < 0) {
    				quarto.ui.commandNumber = 2;
    			}
    			quarto.playSE(8);
    		}
    		if(code == KeyEvent.VK_S) {
    			quarto.ui.commandNumber++;
    			if(quarto.ui.commandNumber > 2) {
    				quarto.ui.commandNumber = 0;
    			}
    			quarto.playSE(8);
    		}
    	}
    	if(quarto.ui.subState == 1) {
    		npcInventory(code);
    		if(code == KeyEvent.VK_ESCAPE) {
    			quarto.ui.subState = 0;
    		}
    	}
    	if(quarto.ui.subState == 2) {
    		playerInventory(code);
    		if(code == KeyEvent.VK_ESCAPE) {
    			quarto.ui.subState = 0;
    		}
    	}
    }
    
    public void playerInventory(int code) {
    	
    	if(code == KeyEvent.VK_W) {
    		if(quarto.ui.playerSlotRow != 0) {
    			quarto.ui.playerSlotRow--;
        		quarto.playSE(8);	
    		}
    	}
    	if(code == KeyEvent.VK_A) {
    		if(quarto.ui.playerSlotCol != 0) {
    			quarto.ui.playerSlotCol--;
        		quarto.playSE(8);	
    		}
    	}
    	if(code == KeyEvent.VK_S) {
    		if(quarto.ui.playerSlotRow != 3) {
    			quarto.ui.playerSlotRow++;
        		quarto.playSE(8);	
    		}
    	}
    	if(code == KeyEvent.VK_D) {
    		if(quarto.ui.playerSlotCol != 4) {
    			quarto.ui.playerSlotCol++;
        		quarto.playSE(8);	
    		}
    	}
    }
    
    public void npcInventory(int code) {
    	
    	if(code == KeyEvent.VK_W) {
    		if(quarto.ui.npcSlotRow != 0) {
    			quarto.ui.npcSlotRow--;
        		quarto.playSE(8);	
    		}
    	}
    	if(code == KeyEvent.VK_A) {
    		if(quarto.ui.npcSlotCol != 0) {
    			quarto.ui.npcSlotCol--;
        		quarto.playSE(8);	
    		}
    	}
    	if(code == KeyEvent.VK_S) {
    		if(quarto.ui.npcSlotRow != 3) {
    			quarto.ui.npcSlotRow++;
        		quarto.playSE(8);	
    		}
    	}
    	if(code == KeyEvent.VK_D) {
    		if(quarto.ui.npcSlotCol != 4) {
    			quarto.ui.npcSlotCol++;
        		quarto.playSE(8);	
    		}
    	}
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F) {
        	shotKeyPressed = false;
        }
    }
    
}
