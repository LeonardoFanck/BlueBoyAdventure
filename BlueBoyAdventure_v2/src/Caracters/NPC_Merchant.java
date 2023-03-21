package Caracters;

import Screens.Quarto;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Potion;
import object.OBJ_Shield;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class NPC_Merchant extends Entity{
	
	public NPC_Merchant(Quarto quarto) {
		
		super(quarto);
		
		direction = "down";
		speed = 1;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		setDialogue();
		setItems();
	}
	
	public void getImage(){
        
        up1 = setup("/npc/merchant_down_1", quarto.tileSize, quarto.tileSize);
        up2 = setup("/npc/merchant_down_2", quarto.tileSize, quarto.tileSize);
        down1 = setup("/npc/merchant_down_1", quarto.tileSize, quarto.tileSize);
        down2 = setup("/npc/merchant_down_2", quarto.tileSize, quarto.tileSize);
        left1 = setup("/npc/merchant_down_1", quarto.tileSize, quarto.tileSize);
        left2 = setup("/npc/merchant_down_2", quarto.tileSize, quarto.tileSize);
        right1 = setup("/npc/merchant_down_1", quarto.tileSize, quarto.tileSize);
        right2 = setup("/npc/merchant_down_2", quarto.tileSize, quarto.tileSize);
    }
    
	public void setDialogue() {
		
		dialogues[0] = "Ohh, estou impressionado, \nvocê conseguiu encontrar este local..";
		dialogues[1] = "Tenho de tudo e mais um pouco, o que gostaria?";
	}
	
	public void setItems() {
		
		inventory.add(new OBJ_Potion(quarto));
		inventory.add(new OBJ_Axe(quarto));
		inventory.add(new OBJ_Sword_Normal(quarto));
		inventory.add(new OBJ_Key(quarto));
		inventory.add(new OBJ_Shield_Wood(quarto));
		inventory.add(new OBJ_Shield(quarto));
	}
	
	public void speak() {
		
		super.speak();
		quarto.gameState = quarto.tradeState;
		quarto.ui.npc = this;
	}
}
