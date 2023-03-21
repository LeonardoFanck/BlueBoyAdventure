package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import Caracters.Entity;
import Screens.Quarto;


public class OBJ_Key extends Entity{
	
	Quarto quarto;

	public OBJ_Key(Quarto quarto) {
		
		super(quarto);
		this.quarto = quarto;
		
		type = typeConsumable;
		name = "fish";
		down1 = setup("/objects/fish", quarto.tileSize, quarto.tileSize);
		description = "[" + name + "]\nDiz chave porém \nparece um peixe.";
		price = 100;
		stackable = true;
	}
	
	public boolean use(Entity entity) {
		
		quarto.gameState = quarto.dialogueState;
		
		int objIndex = getDetected(entity, quarto.obj, "Door");
		
		if(objIndex != 999) {
			quarto.ui.currentDialogue = "Você utilizou " + name + ", conseguiu abrir a porta";
			quarto.playSE(3);
			quarto.obj[quarto.currentMap][objIndex] = null;
			
			return true;
		}
		else {
			quarto.ui.currentDialogue = "Não tem porque utilizar a " + name + " aqui.";
			
			return false;
		}
	}
}
		