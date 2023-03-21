package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Boots extends Entity{;
	
	public OBJ_Boots(Quarto quarto) {
		super(quarto);
		
		name = "Boots";
		down1 = setup("/objects/boots", quarto.tileSize, quarto.tileSize);
		
	}
}
