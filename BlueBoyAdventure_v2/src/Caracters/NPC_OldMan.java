package Caracters;

import java.awt.Rectangle;
import java.util.Random;

import Screens.Quarto;

public class NPC_OldMan extends Entity{

	public NPC_OldMan(Quarto quarto) {
		
		super(quarto);
		
		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 30;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		setDialogue();
	}
	
	public void getImage(){
        
        up1 = setup("/npc/oldman_up_1", quarto.tileSize, quarto.tileSize);
        up2 = setup("/npc/oldman_up_2", quarto.tileSize, quarto.tileSize);
        down1 = setup("/npc/oldman_down_1", quarto.tileSize, quarto.tileSize);
        down2 = setup("/npc/oldman_down_2", quarto.tileSize, quarto.tileSize);
        left1 = setup("/npc/oldman_left_1", quarto.tileSize, quarto.tileSize);
        left2 = setup("/npc/oldman_left_2", quarto.tileSize, quarto.tileSize);
        right1 = setup("/npc/oldman_right_1", quarto.tileSize, quarto.tileSize);
        right2 = setup("/npc/oldman_right_2", quarto.tileSize, quarto.tileSize);
    }
    
	public void setDialogue() {
		
		dialogues[0] = "Olá jovem gato";
		dialogues[1] = "O que faz nesta ilha?";
		dialogues[2] = "Miau?";
		dialogues[3] = "Gostaria de te ajudar, mas não sei \naonde fica este local, me desculpe";
		dialogues[4] = "Gostaria de um peixe?";
	}
	
    public void setAction() {

    	if(onPath == true) {
//    		int goalCol = 12;
//    		int goalRow = 9;
    		int goalCol = (quarto.player.mundoX + quarto.player.solidArea.x) / quarto.tileSize;
    		int goalRow = (quarto.player.mundoY + quarto.player.solidArea.y) / quarto.tileSize;
    		
    		
    		searchPath(goalCol, goalRow);
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
    
    public void speak() {
    	
    	// FAZ ESTE PERSONAGEM FAZER COISAS ESPECÍFICAS (NÃO GENERICO)
    	
    	super.speak(); 
    	
    	onPath = true;
    }
	
}
