package object;

import Caracters.Entity;
import Screens.Quarto;

public class OBJ_Tent extends Entity{

	Quarto quarto;
	
	public OBJ_Tent(Quarto quarto) {
		
		super(quarto);
		this.quarto = quarto;
		
		type = typeConsumable;
		name = "Tenda";
		down1 = setup("/objects/tent", quarto.tileSize, quarto.tileSize);
		description = "[Tenda]\nVocê pode dormir até\na próxima manhã";
		price = 300;
		stackable = true;
	}
	
	public boolean use(Entity entity) {
		
		quarto.gameState = quarto.sleepState;
		quarto.playSE(13);
		quarto.player.life = quarto.player.maxLife;
		quarto.player.mana = quarto.player.maxMana;
		quarto.player.getSleepingImage(down1);
		
		// RETORNA TRUE SE SÓ PODER UTILIZAR UMA VEZ, SENÃO RETORNA FALSO
		return true;
	}
}
