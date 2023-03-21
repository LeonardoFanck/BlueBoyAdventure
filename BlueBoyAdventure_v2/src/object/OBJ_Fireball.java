package object;

import java.awt.Color;

import Caracters.Entity;
import Caracters.Projectile;
import Screens.Quarto;

public class OBJ_Fireball extends Projectile{

	Quarto quarto;
	
	public OBJ_Fireball(Quarto quarto) {
		
		super(quarto); 
		
		this.quarto = quarto;
		
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}
	
	
	public void getImage() {
		
		up1 = setup("/projectile/fireball_up_1", quarto.tileSize, quarto.tileSize);
		up2 = setup("/projectile/fireball_up_2", quarto.tileSize, quarto.tileSize);
		down1 = setup("/projectile/fireball_down_1", quarto.tileSize, quarto.tileSize);
		down2 = setup("/projectile/fireball_down_2", quarto.tileSize, quarto.tileSize);
		left1 = setup("/projectile/fireball_left_1", quarto.tileSize, quarto.tileSize);
		left2 = setup("/projectile/fireball_left_2", quarto.tileSize, quarto.tileSize);
		right1 = setup("/projectile/fireball_right_1", quarto.tileSize, quarto.tileSize);
		right2 = setup("/projectile/fireball_right_2", quarto.tileSize, quarto.tileSize);
	}
	
	public boolean haveResource(Entity user) {
		
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		}
		
		return haveResource;
	}

	public void subtractResource(Entity user) {
		
		user.mana -= useCost;
	}
	
	public Color getParticleColor() {
		
		Color color = new Color(240, 50, 0);
		
		return color;
	}
	
	public int getParticleSize() {
		
		int size = 10;
		
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
