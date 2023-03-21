package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Coin_Bronze extends Entity{

	Quarto quarto;
	
	public OBJ_Coin_Bronze(Quarto quarto) {
		
		super(quarto);
		
		this.quarto = quarto;
		
		type = typePickupOnly;
		name = "Moeda de Bronze";
		value = 1;
		down1 = setup("/objects/coin_bronze", quarto.tileSize, quarto.tileSize);
	}
	
	public boolean use(Entity entity) {
		
		quarto.playSE(1);
		quarto.ui.addMessage("Moeda: " + value);
		quarto.player.coin += value;
		
		return true;
	}
}
