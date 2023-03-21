package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Door extends Entity{
	
	Quarto quarto;
	
	public OBJ_Door(Quarto quarto) {
		
		super(quarto);
		this.quarto = quarto;
		
		type = typeObstacle;
		name = "Door";
		down1 = setup("/objects/door", quarto.tileSize, quarto.tileSize);
		collision = true;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public void interact() {
		
		quarto.gameState = quarto.dialogueState;
		quarto.ui.currentDialogue = "É necessário uma chave...";
	}
}
