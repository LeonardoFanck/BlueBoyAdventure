package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Shield extends Entity{

	public OBJ_Shield(Quarto quarto) {
		
		super(quarto);
		
		type = typeShield;
		name = "Escudo de Metal";
		down1 = setup("/objects/shield_blue", quarto.tileSize, quarto.tileSize);
		defenseValue = 2;
		description = "[" + name + "]\nUm escudo superior\nao de madeira!";
		
	}

}
