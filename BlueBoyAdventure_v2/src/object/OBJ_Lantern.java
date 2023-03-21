package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Lantern extends Entity{

	Quarto quarto;
	
	public OBJ_Lantern(Quarto quarto) {
		
		super(quarto);
		this.quarto = quarto;
		
		type = typeLight;
		name = "Lanterna";
		down1 = setup("/objects/lantern", quarto.tileSize, quarto.tileSize);
		description = "(Lanterna)\nIlumina ao seu redor";
		price = 200;
		lightRadius = 250;
	}
}
