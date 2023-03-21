package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Shield_Wood extends Entity{

	public OBJ_Shield_Wood(Quarto quarto) {
		
		super(quarto);
		
		type = typeShield;
		name = "Wood Shield";
		down1 = setup("/objects/shield_wood", quarto.tileSize, quarto.tileSize);
		defenseValue = 1;
		description = "[" + name + "]\nUm velho escudo de \nmadeira.";
	}

}
