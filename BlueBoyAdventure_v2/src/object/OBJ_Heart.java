package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Heart extends Entity{

	Quarto quarto;
	
	public OBJ_Heart(Quarto quarto) {
		
		super(quarto);
		
		this.quarto = quarto;
		
		type = typePickupOnly;
		name = "Heart";
		value = 2;
		down1 = setup("/objects/heart_full", quarto.tileSize, quarto.tileSize);
		image = setup("/objects/heart_full", quarto.tileSize, quarto.tileSize);
		image2 = setup("/objects/heart_half", quarto.tileSize, quarto.tileSize);
		image3 = setup("/objects/heart_blank", quarto.tileSize, quarto.tileSize);		
	}
	
	public boolean use(Entity entity) {
		
		quarto.playSE(2);
		quarto.ui.addMessage("Vida +" + value);
		entity.life += value; 
		
		return true;
	}
}
