package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Potion extends Entity{

	Quarto quarto;
	
	public OBJ_Potion(Quarto quarto) {
		
		super(quarto);
		
		this.quarto = quarto;
		
		type = typeConsumable;
		name = "Poção de Cura";
		value = 5;
		down1 = setup("/objects/potion_red", quarto.tileSize, quarto.tileSize);
		description = "[" + name + "]\nUma poção feita a\npartir da água sagrada.";
		price = 25;
		stackable = true;
	}
	
	public boolean use(Entity entity) {
		
		quarto.gameState = quarto.dialogueState;
		quarto.ui.currentDialogue = "Você bebeu a " + name + "!\n"
				+ "Sua vida aumentou em " + value + "!";
		entity.life += value;
		quarto.playSE(2);
		
		return true;
	}

}
