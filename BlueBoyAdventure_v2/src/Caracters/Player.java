/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Caracters;

import Config.KeyHandler;
import Config.UtilityTool;
import Screens.Quarto;
import object.OBJ_Axe;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 *
 * @author Leozin
 */
public class Player extends Entity{
    
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;
    
    public Player(Quarto quarto, KeyHandler keyH){
        
    	super(quarto);
    	
        this.keyH = keyH;
        
        screenX = quarto.larguraTela/2 - (quarto.tileSize/2);
        screenY = quarto.alturaTela/2 - (quarto.tileSize/2);
        
        // SOLID AREA
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 32;
        solidArea.width = 32;
        
        // ATTACK AREA
//        attackArea.width = 36;
//        attackArea.height = 36;
        
        setDefaultValues();
        getplayerImage();
        getPlayerAttackImage();
        setItems();
    }
    
    public void setDefaultValues(){
        
    	quarto.currentMap = 0;
    	mundoX = quarto.tileSize * 22;
    	mundoY = quarto.tileSize * 22;
    	
//        mundoX = quarto.tileSize * 23;
//        mundoY = quarto.tileSize * 22;
    	
    	defaultSpeed = 4;
    	
        speed = defaultSpeed;
        direction = "down";
        
        // STATUS DO PLAYER
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        level = 1;
        ammo = 10;
        strength = 1; // QUANTO MAIOR A FORÇA, MAIOR O DANO
        dexterity = 1; // QUANTO MAIOR A RESISTÊNCIA, MENOR O DANO RECEBIDO
        exp = 0;
        nextLevelExp = 5;
        coin = 200;
//        currentWeapon = new OBJ_Sword_Normal(quarto);
        currentWeapon = new OBJ_Axe(quarto);
        currentShield = new OBJ_Shield_Wood(quarto);
        projectile = new OBJ_Fireball(quarto);
//        projectile = new OBJ_Rock(quarto);
        attack = getAttack(); // O DANO TOTAL DADO, É DETERMINADO PELA FORÇA E ARMA UTILIZADA
        defense = getDefense(); // A DEFESA TOTAL, É DETERMINADA PELA RESISTÊNCIA E ESCUDO USADO
    }
    
    public void setDefaultPositions() {
    	
    	mundoX = quarto.tileSize * 23;
        mundoY = quarto.tileSize * 12;
        direction = "down";
    }
    
    public void restoreLifeAndMana() {
    	
    	life = maxLife;
    	mana = maxMana;
    	invincible = false;
    }
    
    public void setItems() {
    	
    	inventory.clear();
    	inventory.add(currentWeapon);
    	inventory.add(currentShield);
    	inventory.add(new OBJ_Key(quarto));
    	
    }
    
    public int getAttack() {
    
    	attackArea = currentWeapon.attackArea;
    	
    	return attack = strength * currentWeapon.attackValue;
    }
    
    public int getDefense() {
    	
    	return defense = dexterity * currentShield.defenseValue;
    }
    
    public void getplayerImage(){
        
        up1 = setup("/player/boy_up_1", quarto.tileSize, quarto.tileSize);
        up2 = setup("/player/boy_up_2", quarto.tileSize, quarto.tileSize);
        down1 = setup("/player/boy_down_1", quarto.tileSize, quarto.tileSize);
        down2 = setup("/player/boy_down_2", quarto.tileSize, quarto.tileSize);
        left1 = setup("/player/boy_left_1", quarto.tileSize, quarto.tileSize);
        left2 = setup("/player/boy_left_2", quarto.tileSize, quarto.tileSize);
        right1 = setup("/player/boy_right_1", quarto.tileSize, quarto.tileSize);
        right2 = setup("/player/boy_right_2", quarto.tileSize, quarto.tileSize);
    }
    
    public void getSleepingImage(BufferedImage image) {
    	
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }
    
    public void getPlayerAttackImage() {
    	
    	if(currentWeapon.type == typeSword) {
        	attackUp1 = setup("/player/boy_attack_up_1", quarto.tileSize, quarto.tileSize*2);
        	attackUp2 = setup("/player/boy_attack_up_2", quarto.tileSize, quarto.tileSize*2);
        	attackDown1 = setup("/player/boy_attack_down_1", quarto.tileSize, quarto.tileSize*2);
        	attackDown2 = setup("/player/boy_attack_down_2", quarto.tileSize, quarto.tileSize*2);
        	attackLeft1 = setup("/player/boy_attack_left_1", quarto.tileSize*2, quarto.tileSize);
        	attackLeft2 = setup("/player/boy_attack_left_2", quarto.tileSize*2, quarto.tileSize);
        	attackRight1 = setup("/player/boy_attack_right_1", quarto.tileSize*2, quarto.tileSize);
        	attackRight2 = setup("/player/boy_attack_right_2", quarto.tileSize*2, quarto.tileSize);
    	}
    	if(currentWeapon.type == typeAxe) {
        	attackUp1 = setup("/player/boy_axe_up_1", quarto.tileSize, quarto.tileSize*2);
        	attackUp2 = setup("/player/boy_axe_up_2", quarto.tileSize, quarto.tileSize*2);
        	attackDown1 = setup("/player/boy_axe_down_1", quarto.tileSize, quarto.tileSize*2);
        	attackDown2 = setup("/player/boy_axe_down_2", quarto.tileSize, quarto.tileSize*2);
        	attackLeft1 = setup("/player/boy_axe_left_1", quarto.tileSize*2, quarto.tileSize);
        	attackLeft2 = setup("/player/boy_axe_left_2", quarto.tileSize*2, quarto.tileSize);
        	attackRight1 = setup("/player/boy_axe_right_1", quarto.tileSize*2, quarto.tileSize);
        	attackRight2 = setup("/player/boy_axe_right_2", quarto.tileSize*2, quarto.tileSize);
    	}

    }
    
    public void update(){
    	
    	if(attacking == true) {
    		attacking();
    	}
        
    	else if(keyH.upPressed == true || keyH.downPressed == true || 
        		keyH.leftPressed == true || keyH.rightPressed == true ||
        		keyH.enterPressed == true){
            
            if(keyH.upPressed == true){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }
            
            // VERIFICANDO A COLISÃO
            colissionOn = false;
            quarto.cChecker.checkTile(this);
            
            // VERIFIACANDO COLISÃO COM OBJETOS
            int objIndex = quarto.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
        
            // VERIFICANDO COLISÃO COM NPC
            int npcIndex = quarto.cChecker.checkEntity(this, quarto.npc);
            interactNPC(npcIndex);
            
            // VERIFICANDO COLISÃO COM MONSTROS
            int monsterIndex = quarto.cChecker.checkEntity(this, quarto.monster);
            contactMonster(monsterIndex);
            
            // VERIFICANDO COLISÃO COM AS TILE INTERACTIVE
            quarto.cChecker.checkEntity(this, quarto.iTile);
            
            // VERIFICANDO EVENTO
            quarto.eHandler.checkEvent();
            
            // SE A COLISÃO FOR FALSA, PLAYER PODE ANDAR
            if(colissionOn == false && keyH.enterPressed == false){
                
                switch(direction){
                    case "up": mundoY -= speed; break;
                    case "down": mundoY += speed; break;
                    case "left": mundoX -= speed; break;
                    case "right": mundoX += speed; break;
                }
            }
            
            if(keyH.enterPressed == true && attackCanceled == false) {
            	quarto.playSE(2);
            	attacking = true;
            	spriteCounter = 0;
            }
            
            attackCanceled = false;
            quarto.keyH.enterPressed = false;
            
            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNumber == 1){
                    spriteNumber = 2;
                }
                else if(spriteNumber == 2){
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
    	
    	if(quarto.keyH.shotKeyPressed == true && projectile.alive == false && 
    			shotAvailableCounter == 60 && projectile.haveResource(this) == true) {
    		// SETANDO AS COORDENADAS, DIREÇÃO E USUÁRIO 
    		projectile.set(mundoX, mundoY, direction, true, this);
    		
    		// SUBTRAINDO O CUSTO DE MANA
    		projectile.subtractResource(this);
    		
    		// VERIFICANDO VAGA NA LISTA
    		for(int i = 0; i < quarto.projectile[1].length; i++) {
    			if(quarto.projectile[quarto.currentMap][i] == null) {
    				quarto.projectile[quarto.currentMap][i] = projectile;
    				break;
    			}
    		}
    		
    		shotAvailableCounter = 0;
    		
    		quarto.playSE(9);
    	}
        
        // PRECISA SER FORA DAS TECLAS DE COMANDO
        if(invincible == true) {
        	invincibleCounter++;
        	if(invincibleCounter > 60) {
        		invincible = false;
        		invincibleCounter = 0;
        	}
        }
        
        if(shotAvailableCounter < 60) {
        	shotAvailableCounter++;
        }
        
        if(life > maxLife) {
			life = maxLife;
		}
        
        if(mana > maxMana) {
			mana = maxMana;
		}
        
        if(life <= 0) {
        	quarto.gameState = quarto.gameOverState;
        	quarto.playSE(11);
        	quarto.ui.commandNumber = -1;
        	quarto.stopMusic();
        }
    }
    
    public void attacking() {
    	
    	spriteCounter++;
    	
    	if(spriteCounter <= 5) {
    		spriteNumber = 1;
    	}
    	if(spriteCounter > 5 && spriteCounter <= 25) {
    		spriteNumber = 2;
    		
    		// SALVANDO OS DADOS DE POSIÇÃO E AREA DO PERSONAGEM
    		int currentWorldX = mundoX;
    		int currentWorldY = mundoY;
    		int solidAreaWidth = solidArea.width;
    		int solidAreaHeight = solidArea.height;
    		
    		// AJUSTANDO POSIÇÃO DO JOGADOR PARA ATACAR
    		
    		switch(direction) {
    		case "up": mundoY -= attackArea.height; break;
    		case "down": mundoY += attackArea.height; break;
    		case "left": mundoX -= attackArea.width; break;
    		case "right": mundoX += attackArea.width; break;
    		}
    		
    		// AREA DE ATAQUE VIRA A AREA SOLIDA
    		solidArea.width = attackArea.width;
    		solidArea.height = attackArea.height;
    		
    		// VERIFICANDO A COLISÃO COM O MONSTRO COM AS NOVAS POSIÇÕES E AREAS
    		int monsterIndex = quarto.cChecker.checkEntity(this, quarto.monster);
    		damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);
    		
    		// VERIFICANDO A DETECÇÃO DO HIT COM A TILE INTERACTIVE
    		int iTileIndex = quarto.cChecker.checkEntity(this, quarto.iTile);
    		damageInteractiveTile(iTileIndex);
    		
    		// VERIFICANDO COLISÃO COM PROJETEIS
    		int projectileIndex = quarto.cChecker.checkEntity(this, quarto.projectile);
    		damageProjectile(projectileIndex);
    		
    		// DEPOIS DE VERIFICAR A COLISÃO, RESTAURA AS VARIAVEIS PARA O PADRÃO
    		mundoX = currentWorldX;
    		mundoY = currentWorldY;
    		solidArea.width = solidAreaWidth;
    		solidArea.height = solidAreaHeight;
    	}
    	if(spriteCounter > 25) {
    		spriteNumber = 1;
    		spriteCounter = 0;
    		attacking = false;
    	}
    }
    
    public void pickUpObject(int i) {
    	
    	if(i != 999) {
    		// PEGA APENAS ITENS
    		if(quarto.obj[quarto.currentMap][i].type == typePickupOnly) {
    			quarto.obj[quarto.currentMap][i].use(this);
    			quarto.obj[quarto.currentMap][i] = null;
    		}
    		// OBSTACULOS
    		else if(quarto.obj[quarto.currentMap][i].type == typeObstacle) {
    			if(keyH.enterPressed == true) {
    				attackCanceled = true;
    				quarto.obj[quarto.currentMap][i].interact();
    			}
    		}
    		// INVENTARIO DE ITENS
    		else {
    			String text;
        		
        		if(canObtainItem(quarto.obj[quarto.currentMap][i]) == true) {
        			quarto.playSE(1);
        			text = "Você pegou um: " + quarto.obj[quarto.currentMap][i].name + "!";
        		}
        		else {
        			text = "Você não aguenta carregar mais itens";
        		}
        		quarto.ui.addMessage(text);
        		quarto.obj[quarto.currentMap][i] = null;
    		}
    	}
    }
    
    public void interactNPC(int i) {
    	
    	if(quarto.keyH.enterPressed == true) {
    	
    		if(i != 999) {
    			attackCanceled = true;
        		quarto.gameState = quarto.dialogueState;
        		quarto.npc[quarto.currentMap][i].speak();		
        	}
    	}
    }
    
    public void contactMonster(int i) {
    	
    	if(i != 999) {
    		if(invincible == false  && quarto.monster[quarto.currentMap][i].dying == false) {
    			quarto.playSE(6);
    			
    			int damage = quarto.monster[quarto.currentMap][i].attack - defense;
    			if(damage < 0) {
    				damage = 0;
    			}
    			
    			life -= damage;
    			invincible = true;
    		}
    		
    	}
    }
    
    public void damageMonster(int i, int attack, int knockBackPower) {
    	if(i != 999) {
    		if(quarto.monster[quarto.currentMap][i].invincible == false) {
    			quarto.playSE(5);
    			
    			if(knockBackPower > 0) {
    				knockBack(quarto.monster[quarto.currentMap][i], knockBackPower);
    			}
    			    			
    			int damage = attack - quarto.monster[quarto.currentMap][i].defense;
    			if(damage < 0) {
    				damage = 0;
    			}
    			
    			quarto.monster[quarto.currentMap][i].life -= damage;
    			quarto.ui.addMessage(damage + " damage!");	
    			
    			quarto.monster[quarto.currentMap][i].invincible = true;
    			quarto.monster[quarto.currentMap][i].damageReaction();
    			
    			if(quarto.monster[quarto.currentMap][i].life <= 0) {
    				quarto.monster[quarto.currentMap][i].dying = true;
    				quarto.ui.addMessage("Matou um " + quarto.monster[quarto.currentMap][i].name + "!");
    				quarto.ui.addMessage("Exp + " + quarto.monster[quarto.currentMap][i].exp);
    				exp += quarto.monster[quarto.currentMap][i].exp;
    				checkLevelUp();
    			}
    		}
    	}
    }
    
    public void knockBack(Entity entity, int knockBackPower) {
    	
    	entity.direction = direction;
    	entity.speed += knockBackPower;
    	entity.knockBack = true;
    }
    
    public void damageInteractiveTile(int i) {
    	
    	if(i != 999 && quarto.iTile[quarto.currentMap][i].destructible == true && 
    			quarto.iTile[quarto.currentMap][i].isCorrectItem(this) == true && quarto.iTile[quarto.currentMap][i].invincible == false) {
    		quarto.iTile[quarto.currentMap][i].playSE(); 
    		quarto.iTile[quarto.currentMap][i].life--;
    		quarto.iTile[quarto.currentMap][i].invincible = true;
    		
    		// GERANDO AS PARTICULAS 
    		generateParticle(quarto.iTile[quarto.currentMap][i], quarto.iTile[quarto.currentMap][i]);
    		
    		if(quarto.iTile[quarto.currentMap][i].life == 0) {
    			quarto.iTile[quarto.currentMap][i] = quarto.iTile[quarto.currentMap][i].getDestroyedForm();
    		}
    		
    	}
    }
    
    public void damageProjectile(int i) {
    	
    	if(i != 999) {
    		Entity projectile = quarto.projectile[quarto.currentMap][i];
    		projectile.alive = false;
    		generateParticle(projectile, projectile);
    	}
    }
    
    public void checkLevelUp() {
    	
    	if(exp >= nextLevelExp) {
    		level++;
    		nextLevelExp = nextLevelExp*2;
    		maxLife += 2;
    		strength++;
    		dexterity++;
    		attack = getAttack();
    		defense = getDefense();
    		
    		quarto.playSE(7);
    		quarto.gameState = quarto.dialogueState;
    		quarto.ui.currentDialogue = "Você subiu para o Nível: " + level + "!\n"
    				+ "Você se sente mais forte agora";
    	}
    }
    
    public void selectItem() {
    	
    	int itemIndex = quarto.ui.getItemIndexOnSlot(quarto.ui.playerSlotCol, quarto.ui.playerSlotRow);
    	
    	if(itemIndex < inventory.size()) {
    		Entity selectedItem = inventory.get(itemIndex);
    		
    		if(selectedItem.type == typeSword || selectedItem.type == typeAxe) {
    			currentWeapon = selectedItem;
    			attack = getAttack();
    			getPlayerAttackImage();
    		}
    		if(selectedItem.type == typeShield) {
    			currentShield = selectedItem;
    			defense = getDefense();
    		}
    		if(selectedItem.type == typeLight) {
    			if(currentLight == selectedItem) {
    				currentLight = null; 
    			}
    			else {
    				currentLight = selectedItem;
    			}
    			lightUpdated = true;
    		}
    		if(selectedItem.type == typeConsumable) {
    			if(selectedItem.use(this) == true) {
    				if(selectedItem.amount > 1) {
    					selectedItem.amount--;
    				}
    				else {
    					inventory.remove(itemIndex);
    				}
    			}
    		}
    	}
    }
    
    public int searchItemInInventory(String itemName) {
    	
    	int itemIndex = 999;
    	
    	for(int i = 0; i < inventory.size(); i++) {
    		if(inventory.get(i).name.equals(itemName)) {
    			itemIndex = i;
    			break;
    		}
    	}
    	
    	return itemIndex;
    }
    
    public boolean canObtainItem(Entity item) {
    	
    	boolean canObtain = false;
    	
    	// VERIFICA SE O ITEM É STACKAVEL
    	if(item.stackable == true) {
    		int index = searchItemInInventory(item.name);
    		
    		if(index != 999) {
    			inventory.get(index).amount++;
    			canObtain = true;
    		}
    		else { // NOVO ITEM, ENTÃO É NECESSÁRIO VERIFICAR SE TEM UM SLOT LIVRE
    			if(inventory.size() != maxInventorySize) {
    				inventory.add(item);
    				canObtain = true;
    			}
    		}
    	}
    	else { // NÃO STACKAVEL, ENTÃO É NECESSÁRIO VERIFICAR SE TEM UM SLOT LIVRE
    		if(inventory.size() != maxInventorySize) {
				inventory.add(item);
				canObtain = true;
			}
    	}
    	
    	return canObtain;
    }
    
    public void draw(Graphics2D g2){
 
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        switch(direction){
            case "up":
                if(attacking == false) {
                	if(spriteNumber == 1){ image = up1; }
                    if(spriteNumber == 2){ image = up2; }
                }
                if(attacking == true) {
                	tempScreenY = screenY - quarto.tileSize;
                	if(spriteNumber == 1){ image = attackUp1; }
                    if(spriteNumber == 2){ image = attackUp2; }
                }
                break;
            case "down":
                if(attacking == false) {
                	if(spriteNumber == 1){ image = down1; }
                    if(spriteNumber == 2){ image = down2; }
                }
                if(attacking == true) {
                	if(spriteNumber == 1){ image = attackDown1; }
                    if(spriteNumber == 2){ image = attackDown2; }
                }
                break;
            case "left":
                if(attacking == false) {
                	if(spriteNumber == 1){ image = left1; }
                    if(spriteNumber == 2){ image = left2; }
                }
                if(attacking == true) {
                	tempScreenX = screenX - quarto.tileSize;
                	if(spriteNumber == 1){ image = attackLeft1; }
                    if(spriteNumber == 2){ image = attackLeft2; }
                }
                break;
            case "right":
                if(attacking == false) {
                	if(spriteNumber == 1){ image = right1; }
                    if(spriteNumber == 2){ image = right2; }
                }
                if(attacking == true) {
                	if(spriteNumber == 1){ image = attackRight1; }
                    if(spriteNumber == 2){ image = attackRight2; }
                }
                break; 
        }
        
        if(invincible == true) {
        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        
        
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        
        // RESETANDO ALPHA COMPOSITE
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        // DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible:" + invincibleCounter, 10, 400);
    }
    
    
}
