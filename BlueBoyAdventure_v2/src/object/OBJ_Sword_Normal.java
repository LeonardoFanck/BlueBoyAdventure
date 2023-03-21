package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Sword_Normal extends Entity{

	public OBJ_Sword_Normal(Quarto quarto) {
		
		super(quarto);
		
		type = typeSword;
		name = "Normal Sword";
		down1 = setup("/objects/sword_normal", quarto.tileSize, quarto.tileSize);
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "[" + name + "]\nUma velha espada.";
		knockBackPower = 3;
	}
}
