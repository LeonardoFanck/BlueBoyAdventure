package tileInteractive;

import java.awt.Color;

import Caracters.Entity;
import Screens.Quarto;

public class IT_DryTree extends InteractiveTile{

	Quarto quarto;
	
	public IT_DryTree(Quarto quarto, int col, int row) {
		
		super(quarto, col, row);
		
		this.quarto = quarto;
		this.mundoX = quarto.tileSize * col;
		this.mundoY = quarto.tileSize * row;
		
		down1 = setup("/tilesInteractive/drytree", quarto.tileSize, quarto.tileSize);
		destructible = true;
		life = 3;
	}
	
	public boolean isCorrectItem(Entity entity) {
		
		boolean isCorrectItem = false;
		
		if(entity.currentWeapon.type == typeAxe) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
	}
	
	public void playSE() {
		quarto.playSE(10);
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Trunk(quarto, mundoX/quarto.tileSize, mundoY/quarto.tileSize);
		
		return tile;
	}
	
	public Color getParticleColor() {
		Color color = new Color(65, 50, 30);
		
		return color;
	}
	
	public int getParticleSize() {
		
		int size = 6; // 6 PIXELS
		
		return size;
	}
	
	public int getParticleSpeed() {
		
		int speed = 1;
		
		return speed;
	}
	
	public int getParticleMaxLife() {
		
		int maxLife = 20;
		
		return maxLife;
	}
}
