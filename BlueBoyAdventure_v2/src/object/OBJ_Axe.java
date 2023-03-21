package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Axe extends Entity{

	public OBJ_Axe(Quarto quarto) {
		
		super(quarto);
		
		type = typeAxe;
		name = "Machado";
		down1 = setup("/objects/axe", quarto.tileSize, quarto.tileSize);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]\nUm velho machado\npara cortar madeira";
		price = 75;
		knockBackPower = 10;
	}
}
