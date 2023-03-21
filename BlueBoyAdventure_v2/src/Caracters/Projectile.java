package Caracters;

import Screens.Quarto;

public class Projectile extends Entity{

	Entity user;
	
	public Projectile(Quarto quarto) {
		
		super(quarto);
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		
		this.mundoX = worldX;
		this.mundoY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife; // RESETA A VIDA TODA VEZ Q ATIRAR UM NOVO
	}
	
	public void update() {
		
		if(user == quarto.player) {
			int monsterIndex = quarto.cChecker.checkEntity(this, quarto.monster);
			if(monsterIndex != 999) {
				quarto.player.damageMonster(monsterIndex, attack, knockBackPower);
				generateParticle(user.projectile, quarto.monster[quarto.currentMap][monsterIndex]);
				alive = false;
			}
		}
		if(user != quarto.player) {
			boolean contactPlayer = quarto.cChecker.checkPlayer(this);
			if(quarto.player.invincible == false && contactPlayer == true) {
				damagePlayer(attack);
				generateParticle(user.projectile, quarto.player);
				alive = false;
			}
		}
		
		switch(direction) {
		case "up": mundoY -= speed; break;
		case "down": mundoY += speed; break;
		case "left": mundoX -= speed; break;
		case "right": mundoX += speed; break;
		}
		
		life--;
		if(life <= 0) {
			alive = false;
		}
		
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNumber == 1) {
				spriteNumber = 2;
			}
			else if(spriteNumber == 2) {
				spriteNumber = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public boolean haveResource(Entity user) {
		
		boolean haveResource = false;
		
		return haveResource;
	}
	
	public void subtractResource(Entity user) {}
}
