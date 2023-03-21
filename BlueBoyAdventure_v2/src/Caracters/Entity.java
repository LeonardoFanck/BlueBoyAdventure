/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Caracters;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Config.UtilityTool;
import Screens.Quarto;

/**
 *
 * @author Leozin
 */
public class Entity {
   
	Quarto quarto;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    String dialogues[] = new String[20];
    public BufferedImage image, image2, image3;
    public boolean collision = false;
    
    // ESTADO
    public int mundoX, mundoY;
    public String direction = "down";
    public int spriteNumber = 1;
    int dialogueIndex = 0;
    public boolean colissionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    
    // CONTADORES
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;
    
    // ATRIBUTOS DO PESONAGEM
    public int maxLife;
    public int defaultSpeed;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public String name;
    public int speed;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;
    
    // ATRIBUTOS DOS ITENS
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;
    
    // TYPE
    public int type; // 0 = player; 1 = NPC; 2 = MONSTER 
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;
    public final int typePickupOnly = 7;
    public final int typeObstacle = 8;
    public final int typeLight = 9;
    
    public Entity(Quarto quarto) {
    	this.quarto = quarto;
    }
    
    public int getLeftX() {
    	
    	return mundoX + solidArea.x;
    }
    
    public int getRightX() {
    	
    	return mundoX + solidArea.x + solidArea.width;
    }
    
    public int getTopY() {
    	
    	return mundoY + solidArea.y;
    }
    
    public int getBottomY() {
    	
    	return mundoY + solidArea.y + solidArea.height;
    }
    
    public int getCol() {
    	
    	return (mundoX + solidArea.x) / quarto.tileSize;
    }
    
    public int getRow() {
    	
    	return (mundoY + solidArea.y) / quarto.tileSize;
    }
    
    public void setAction() {}
    public void damageReaction() {}
    public boolean use(Entity entity) {
    	
    	return false;
    }
    public void checkDrop() {}
    
    public Color getParticleColor() {
		Color color= null;
		
		return color;
	}
	
	public int getParticleSize() {
		
		int size = 0;
		
		return size;
	}
	
	public int getParticleSpeed() {
		
		int speed = 0;
		
		return speed;
	}
	
	public int getParticleMaxLife() {
		
		int maxLife = 0;
		
		return maxLife;
	}
    
    public void dropItem(Entity droppedItem) {
    	
    	for(int i = 0; i < quarto.obj[1].length; i++) {
    		if(quarto.obj[quarto.currentMap][i] == null) {
    			quarto.obj[quarto.currentMap][i] = droppedItem;
    			quarto.obj[quarto.currentMap][i].mundoX = mundoX; // O MUNDOX E O MUNDOY É DO MONSTRO MORTO
    			quarto.obj[quarto.currentMap][i].mundoY = mundoY;
    			break;
    		}
    	}
    }
    
    public void generateParticle(Entity generator, Entity target) {
    	
    	Color color = generator.getParticleColor();
    	int size = generator.getParticleSize();
    	int speed = generator.getParticleSpeed();
    	int maxLife = generator.getParticleMaxLife();
    	
    	Particle p1 = new Particle(quarto, target, color, size, speed, maxLife, -2, -1);
    	quarto.particleList.add(p1);
    	
    	Particle p2 = new Particle(quarto, target, color, size, speed, maxLife, 2, -1);
    	quarto.particleList.add(p2);
    	
    	Particle p3 = new Particle(quarto, target, color, size, speed, maxLife, -2, 1);
    	quarto.particleList.add(p3);
    	
    	Particle p4 = new Particle(quarto, target, color, size, speed, maxLife, 2, 1);
    	quarto.particleList.add(p4);
    }
    
    public void speak() {
    	
    	if(dialogues[dialogueIndex] == null) {
    		dialogueIndex = 0;
    	}
    	quarto.ui.currentDialogue = dialogues[dialogueIndex];
    	dialogueIndex++;
    	
    	switch(quarto.player.direction) {
    	case "up":
    		direction = "down";
    		break;
    	case "down":
    		direction = "up";
    		break;
    	case "left":
    		direction = "right";
    		break;
    	case "right":
    		direction = "left";
    		break;
    	}
    }
    
    public void interact() {
    	
    	
    }
    
    public void checkCollision() {
    	
    	colissionOn = false;
    	quarto.cChecker.checkTile(this);
    	quarto.cChecker.checkObject(this, false);
    	quarto.cChecker.checkEntity(this, quarto.npc);
    	quarto.cChecker.checkEntity(this, quarto.monster);
    	quarto.cChecker.checkEntity(this, quarto.iTile);
    	boolean contactPlayer = quarto.cChecker.checkPlayer(this);
    	
    	if(this.type == typeMonster && contactPlayer == true) {
    		damagePlayer(attack);
    	}
    }
    
    public void update() {
    	
    	if(knockBack == true) {
    		checkCollision();
    		
    		if(colissionOn == true) {
    			knockBackCounter = 0;
    			knockBack = false;
    			speed = defaultSpeed;
    		}
    		else if(colissionOn == false) {
    			switch(quarto.player.direction) {
	    			case "up":
	                    mundoY -= speed;
	                    break;
	                case "down":
	                    mundoY += speed;
	                    break;
	                case "left":
	                    mundoX -= speed;
	                    break;
	                case "right":
	                    mundoX += speed;
	                    break;
    			}
    		}
    		
    		knockBackCounter++;
    		if(knockBackCounter == 10) {
    			knockBackCounter = 0;
    			knockBack = false;
    			speed = defaultSpeed;
    		}
    	}
    	
    	else {
        	setAction();
        	checkCollision();

        	if(colissionOn == false){
                
                switch(direction){
                    case "up":
                        mundoY -= speed;
                        break;
                    case "down":
                        mundoY += speed;
                        break;
                    case "left":
                        mundoX -= speed;
                        break;
                    case "right":
                        mundoX += speed;
                        break;
                }
            }
    	}
    	

        
        spriteCounter++;
        if(spriteCounter > 24){
            if(spriteNumber == 1){
                spriteNumber = 2;
            }
            else if(spriteNumber == 2){
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
        
        if(invincible == true) {
        	invincibleCounter++;
        	if(invincibleCounter > 40) {
        		invincible = false;
        		invincibleCounter = 0;
        	}
        }	
        if(shotAvailableCounter < 60) {
        	shotAvailableCounter++;
        }
    }
    
    public void damagePlayer(int attack) {
    	
    	if(quarto.player.invincible == false) {
			// PODEMOS RECEBER DANO
			quarto.playSE(6);
			
			int damage = attack - quarto.player.defense;
			if(damage < 0) {
				damage = 0;
			}
			
			quarto.player.life -= damage;
			quarto.player.invincible = true;
		}
    }
    
    public void draw(Graphics2D g2) {	
    	
    	BufferedImage image = null;
    	int screenX = mundoX - quarto.player.mundoX + quarto.player.screenX;
        int screenY = mundoY - quarto.player.mundoY + quarto.player.screenY;
        
        if(mundoX + quarto.tileSize > quarto.player.mundoX - quarto.player.screenX &&
           mundoX - quarto.tileSize < quarto.player.mundoX + quarto.player.screenX && 
           mundoY + quarto.tileSize > quarto.player.mundoY - quarto.player.screenY && 
           mundoY - quarto.tileSize < quarto.player.mundoY + quarto.player.screenY){
        	
        	switch(direction){
	        case "up": 
	           	if(spriteNumber == 1){ image = up1; }
	            if(spriteNumber == 2){ image = up2; }
	            break;
	        case "down":
	        	if(spriteNumber == 1){ image = down1; }
	            if(spriteNumber == 2){ image = down2; }
	            break;
	        case "left":
	        	if(spriteNumber == 1){ image = left1; }
	        	if(spriteNumber == 2){ image = left2; }
	        	break;
	        case "right":
	        	if(spriteNumber == 1){ image = right1; }
	        	if(spriteNumber == 2){ image = right2; }
	        	break;    
	        }
        	
        	// BARRA DE HP DOS MONSTROS
        	if(type == 2 && hpBarOn == true) {
        		
        		double oneScale = (double) quarto.tileSize/maxLife;
        		double hpBarValue = oneScale*life;
        		
        		g2.setColor(new Color(35, 35, 35));
        		g2.fillRect(screenX-1, screenY-11, quarto.tileSize+2, 12);
        		
        		g2.setColor(new Color(255, 0, 30));
            	g2.fillRect(screenX, screenY - 10, (int) hpBarValue, 10);
            	
            	hpBarCounter++;
            	
            	if(hpBarCounter > 600) {
            		hpBarOn = false;
            	}
        	}
        	
        	
        	if(invincible == true) {
        		hpBarOn = true;
        		hpBarCounter = 0;
            	changeAlpha(g2, 0.4F);
            }
        	
        	if(dying == true) {
        		dyingAnimation(g2);
        	}
        	
        	g2.drawImage(image, screenX, screenY, null);
        	
        	changeAlpha(g2, 1F);
        }
    }	
    
    public void dyingAnimation(Graphics2D g2) {
    	
    	dyingCounter++;
    	
    	int i = 5;
    	
    	if(dyingCounter <= i) {changeAlpha(g2, 1f); }
    	if(dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 0f); }
    	if(dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2, 1f); }
    	if(dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2, 0f); }
    	if(dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 1f); }
    	if(dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 0f); }
    	if(dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 1f); }
    	if(dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 0f); }
    	if(dyingCounter > i*8) {
    		alive = false;
    	}
    }
    
    public void changeAlpha(Graphics2D g2, float alphaValue) {
    	
    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    
    
    public BufferedImage setup(String imagePath, int width, int height) {
    	
    	UtilityTool uTool = new UtilityTool();
    	BufferedImage image = null;
    	
    	try {
    		image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
    		image = uTool.scaleImage(image, width, height);
    	}
    	catch (IOException e) {
			e.getStackTrace();
		}
    	
    	return image;
    }
    
    public void searchPath(int goalCol, int goalRow) {
    	
    	int startCol = (mundoX + solidArea.x)/quarto.tileSize;
    	int startRow = (mundoY + solidArea.y)/quarto.tileSize;
    	
    	quarto.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
    	
    	if(quarto.pFinder.search() == true) {
    		// PROXIMO MUNDOX E MUNDOY
    		int nextX = quarto.pFinder.pathList.get(0).col * quarto.tileSize;
    		int nextY = quarto.pFinder.pathList.get(0).row * quarto.tileSize;
    		
    		// POSIÇÃO DA SolidArea das ENTIDADES
    		int enLeftX = mundoX + solidArea.x;
    		int enRightX = mundoX + solidArea.x + solidArea.width;
    		int enTopY = mundoY + solidArea.y;
    		int enBottomY = mundoY + solidArea.y + solidArea.height;
    		
    		if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + quarto.tileSize) {
    			direction = "up";
    		}
    		else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + quarto.tileSize) {
    			direction = "down";
    		}
    		else if(enTopY >= nextY && enBottomY < nextY + quarto.tileSize) {
    			// ESQUERDA OU DIREITA
    			if(enLeftX > nextX) {
    				direction = "left";
    			}
    			if(enLeftX < nextX) {
    				direction = "right";
    			}
    		}
    		else if(enTopY > nextY && enLeftX > nextX) {
    			// PRA CIMA OU ESQUERDA
    			direction = "up";
    			checkCollision();
    			if(colissionOn == true) {
    				direction = "left";
    			}
    		}
    		else if(enTopY > nextY && enLeftX < nextX) {
    			// PRA CIMA OU DIREITA
    			direction = "up";
    			checkCollision();
    			if(colissionOn == true) {
    				direction = "right";
    			}
    		}
    		else if(enTopY < nextY && enLeftX > nextX) {
    			// PARA BAIXO OU ESQUERDA
    			direction = "down";
    			checkCollision();
    			if(colissionOn == true) {
    				direction = "left";
    			}
    		}
    		else if(enTopY < nextY && enLeftX < nextX) {
    			// PARA BAIXO OU DIREITA
    			direction = "down";
    			checkCollision();
    			if(colissionOn == true) {
    				direction = "right";
    			}
    		}
    		
    		// SE CHEGAR AO OBJETIVO, PARAR A BUSCA
//    		int nextCol = quarto.pFinder.pathList.get(0).col;
//    		int nextRow = quarto.pFinder.pathList.get(0).row;
//    		if(nextCol == goalCol && nextRow == goalRow) {
//    			onPath = false;
//    		}
    	}
    }
    
    public int getDetected(Entity user, Entity target[][], String targetName) {
    	
    	int index = 999;
    	
    	// VERIFICA OS ARREDORES DO OBJETO
    	int nextMundoX = user.getLeftX();
    	int nextMundoY = user.getTopY();
    	
    	switch(user.direction) {
    	case "up": nextMundoY = user.getTopY()-1; break;
    	case "down": nextMundoY = user.getBottomY()+1; break;
    	case "left": nextMundoX = user.getLeftX()-1; break;
    	case "right": nextMundoX = user.getRightX()+1; break;
    	}
    	
    	int col = nextMundoX / quarto.tileSize;
    	int row = nextMundoY / quarto.tileSize;
    	
    	for(int i = 0; i < target[1].length; i++) {
    		if(target[quarto.currentMap][i] != null) {
    			if(target[quarto.currentMap][i].getCol() == col &&
    					target[quarto.currentMap][i].getRow() == row &&
    					target[quarto.currentMap][i].name.equals(targetName)) {
	    			index = i;
	    			break; 
    			}
    		}
    	}
    	
    	return index;
    }
}
