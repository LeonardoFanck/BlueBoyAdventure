package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Chest extends Entity{
	
	Quarto quarto;
	Entity loot;
	Boolean opened = false;
	
	public OBJ_Chest(Quarto quarto, Entity loot) {
		
		super(quarto);
		this.quarto = quarto;
		this.loot = loot;
		
		type = typeObstacle;
		name = "Chest";
		image = setup("/objects/chest", quarto.tileSize, quarto.tileSize);
		image2 = setup("/objects/chest_opened", quarto.tileSize, quarto.tileSize);
		down1 = image;
		collision = true;
		
		solidArea.x = 4;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public void interact() {
		
		quarto.gameState = quarto.dialogueState;
		
		if(opened == false) {
			quarto.playSE(3);
			
			StringBuilder sb = new StringBuilder();
			sb.append("Você abriu um baú.. encontrou um " + loot.name + "!");
			
			if(quarto.player.canObtainItem(loot) == false) {
				sb.append("\n...Mas você não pode carregar mais itens.");
			}
			else {
				sb.append("\nVocê obteve " + loot.name + "!");
				down1 = image2;
				opened = true;
			}
			
			quarto.ui.currentDialogue = sb.toString();
		}
		else {
			quarto.ui.currentDialogue = "O baú está vazio...";
		}
	}
}
