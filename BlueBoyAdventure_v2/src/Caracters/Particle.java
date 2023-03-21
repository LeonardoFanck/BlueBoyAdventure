package Caracters;

import java.awt.Color;
import java.awt.Graphics2D;

import Screens.Quarto;

public class Particle extends Entity{

	Entity generator;
	Color color;
	int size;
	int xd;
	int yd;
	
	public Particle(Quarto quarto, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
		
		super(quarto);
		this.generator = generator;
		this.color = color;
		this.size = size;
		this.speed = speed;
		this.maxLife = maxLife;
		this.xd = xd;
		this.yd = yd;
		
		life = maxLife;
		int offSet = (quarto.tileSize/2) - (size/2);
		mundoX = generator.mundoX + offSet;
		mundoY = generator.mundoY + offSet;
	}
	
	public void update() {
		
		life--;
		
		if(life < maxLife/3) {
			yd++;
		}
		
		mundoX += xd * speed;
		mundoY += yd * speed;
	
		if(life == 0) {
			alive = false;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int screenX = mundoX - quarto.player.mundoX + quarto.player.screenX;
		int screenY = mundoY - quarto.player.mundoY + quarto.player.screenY;
		
		g2.setColor(color);
		g2.fillRect(screenX, screenY, size, size);
	}

}
