package tileInteractive;

import Screens.Quarto;

public class IT_Trunk extends InteractiveTile{

	Quarto quarto;
	
	public IT_Trunk(Quarto quarto, int col, int row) {
		
		super(quarto, col, row);
		
		this.quarto = quarto;
		this.mundoX = quarto.tileSize * col;
		this.mundoY = quarto.tileSize * row;
		
		down1 = setup("/tilesInteractive/trunk", quarto.tileSize, quarto.tileSize);
		
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
