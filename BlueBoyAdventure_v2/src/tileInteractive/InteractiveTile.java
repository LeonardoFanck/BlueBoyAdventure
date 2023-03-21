package tileInteractive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Caracters.Entity;
import Screens.Quarto;

public class InteractiveTile extends Entity{
	
	Quarto quarto;
	public boolean destructible = false;
	
	public InteractiveTile(Quarto quarto, int col, int row) {
		
		super(quarto);
		this.quarto = quarto; 
	}
	
	public boolean isCorrectItem(Entity entity) {
		
		boolean isCorrectItem = false;
		
		return isCorrectItem;
	}
	
	public void playSE() {
		
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		
		return tile;
	}
	
	public void update() {
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 20) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
    	int screenX = mundoX - quarto.player.mundoX + quarto.player.screenX;
        int screenY = mundoY - quarto.player.mundoY + quarto.player.screenY;
        
        if(mundoX + quarto.tileSize > quarto.player.mundoX - quarto.player.screenX &&
           mundoX - quarto.tileSize < quarto.player.mundoX + quarto.player.screenX && 
           mundoY + quarto.tileSize > quarto.player.mundoY - quarto.player.screenY && 
           mundoY - quarto.tileSize < quarto.player.mundoY + quarto.player.screenY){
        		
        	g2.drawImage(down1, screenX, screenY, null);
        	
        	changeAlpha(g2, 1F);
        }
	}

}
