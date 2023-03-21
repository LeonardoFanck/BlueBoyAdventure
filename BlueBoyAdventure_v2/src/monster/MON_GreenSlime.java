package monster;

import java.util.Random;

import Caracters.Entity;
import Screens.Quarto;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_GreenSlime extends Entity{

	Quarto quarto;
	
	public MON_GreenSlime(Quarto quarto) {
		
		super(quarto);
		
		this.quarto = quarto;
		
		type = typeMonster;
		name = "Green Slime";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 4;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 2;
		projectile = new OBJ_Rock(quarto);
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	public void getImage() {
		
		up1 = setup("/monster/greenslime_down_1", quarto.tileSize, quarto.tileSize);
		up2 = setup("/monster/greenslime_down_2", quarto.tileSize, quarto.tileSize);
		down1 = setup("/monster/greenslime_down_1", quarto.tileSize, quarto.tileSize);
		down2 = setup("/monster/greenslime_down_2", quarto.tileSize, quarto.tileSize);
		left1 = setup("/monster/greenslime_down_1", quarto.tileSize, quarto.tileSize);
		left2 = setup("/monster/greenslime_down_2", quarto.tileSize, quarto.tileSize);
		right1 = setup("/monster/greenslime_down_1", quarto.tileSize, quarto.tileSize);
		right2 = setup("/monster/greenslime_down_2", quarto.tileSize, quarto.tileSize);
	}
	
	public void update() {
		
		super.update();
		
		int xDistance = Math.abs(mundoX - quarto.player.mundoX);
		int yDistance = Math.abs(mundoY - quarto.player.mundoY);
		int tileDistance = (xDistance + yDistance) / quarto.tileSize;
		
		if(onPath == false && tileDistance < 5) {
			int i = new Random().nextInt(100)+1;
			if(i > 50) {
				onPath = true;
			}
		}
//		if(onPath == true && tileDistance > 20) {
//			onPath = false;
//		}
	}
	
	public void setAction() {
		
		if(onPath == true) {

    		int goalCol = (quarto.player.mundoX + quarto.player.solidArea.x) / quarto.tileSize;
    		int goalRow = (quarto.player.mundoY + quarto.player.solidArea.y) / quarto.tileSize;
    		
    		searchPath(goalCol, goalRow);
    		
    		int i = new Random().nextInt(200) + 1;
        	if(i > 197 && projectile.alive == false && shotAvailableCounter == 60) {
        		projectile.set(mundoX, mundoY, direction, true, this);
//        		quarto.projectileList.add(projectile);
        		
        		// VERIFICANDO VAGA NA LISTA
        		for(int j = 0; j < quarto.projectile[1].length; j++) {
        			if(quarto.projectile[quarto.currentMap][j] == null) {
        				quarto.projectile[quarto.currentMap][j] = projectile;
        				break;
        			}
        		}
        		
        		shotAvailableCounter = 0;
        	}
    	}
    	else {
    		actionLockCounter ++;
        	
        	if(actionLockCounter == 120) {
        	
        		Random random = new Random();
            	int i = random.nextInt(120)+1; // ESCOLHE UM NÚMERO DE 1 A 100
            	
            	if(i <= 25) {	
            		direction = "up";
            	}
            	if(i > 25 && i <= 50) {
            		direction = "down";
            	}
            	if(i > 50 && i <= 75) {
            		direction = "left";
            	}
            	if(i > 75 && i <= 100) {
            		direction = "right";
            	}
            	
            	actionLockCounter = 0; 
        	}
    	}
	}
	
	public void damageReaction() {
		
		actionLockCounter = 0;
//		direction = quarto.player.direction;
		onPath = true;
	}
	
	public void checkDrop() {
		
		// ATIVA AO MORRER
		int i = new Random().nextInt(100)+1;
		
		// SETA O DROP DO MONSTRO
		if(i < 50) {
			dropItem(new OBJ_Coin_Bronze(quarto));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(quarto));
		}
		if(i >= 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(quarto));
		}
	}
}
