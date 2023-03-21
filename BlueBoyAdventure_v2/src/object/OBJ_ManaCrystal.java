package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_ManaCrystal extends Entity{
	
	Quarto quarto;
	
	public OBJ_ManaCrystal(Quarto quarto) {
		
		super(quarto);
		
		this.quarto = quarto;
		
		type = typePickupOnly;
		name = "Cristal de Mana";
		value = 1;
		down1 = setup("/objects/manacrystal_full", quarto.tileSize, quarto.tileSize);
		image = setup("/objects/manacrystal_full", quarto.tileSize, quarto.tileSize);
		image2 = setup("/objects/manacrystal_blank", quarto.tileSize, quarto.tileSize);
	}
	
	public boolean use(Entity entity) {
		
		quarto.playSE(2);
		quarto.ui.addMessage("Mana +" + value);
		entity.mana += value; 
		
		return true;
	}

}
