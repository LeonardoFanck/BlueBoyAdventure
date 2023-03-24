package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Screens.Quarto;

public class Map extends tileManager{

	Quarto quarto;
	BufferedImage worldMap[];
	public boolean miniMapOn = false;
	
	public Map(Quarto quarto) {
		
		super(quarto);
		this.quarto = quarto;
		
		createWorldMap();
	}
	
	public void createWorldMap() {
		
		worldMap = new BufferedImage[quarto.maxMap];
		int worldMapWidth = quarto.tileSize * quarto.maxWorldCollum;
		int worldMapHeight = quarto.tileSize * quarto.maxWorldRow;
		
		for(int i = 0; i < quarto.maxMap; i++) {
			
			worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();
			
			int col = 0;
			int row = 0;
			
			while(col < quarto.maxWorldCollum && row < quarto.maxWorldRow) {
				
				int tileNum = mapTileNum[i][col][row];
				int x = quarto.tileSize * col;
				int y = quarto.tileSize * row;
				g2.drawImage(tile[tileNum].image, x, y, null);
				
				col++;
				if(col == quarto.maxWorldCollum) {
					col = 0;
					row++;
				}
			}
		}
	}
	
	public void drawFullMapScreen(Graphics2D g2) {
		
		// BACKGROUND COLOR
		g2.setColor(Color.black);
		g2.fillRect(0, 0, quarto.larguraTela, quarto.alturaTela);
		
		// DRAW MAP
		int width = 500;
		int height = 500;
		int x = quarto.larguraTela/2 - width/2;
		int y = quarto.alturaTela/2 - height/2;
		g2.drawImage(worldMap[quarto.currentMap], x, y, width, height, null);
		
		// DRAW PLAYER
		double scale = (double) (quarto.tileSize * quarto.maxWorldCollum)/width;
		int playerX = (int)(x + quarto.player.mundoX/scale);
		int playerY = (int)(y + quarto.player.mundoY/scale);
		int playerSize = (int)(quarto.tileSize/scale);
		g2.drawImage(quarto.player.down1, playerX, playerY, playerSize, playerSize, null);
		
		// DICA
		g2.setFont(quarto.ui.maruMonica.deriveFont(32f));
		g2.setColor(Color.white);
		g2.drawString("Pressione M para fechar", 750, 550);
	}
	
	public void drawMiniMap(Graphics2D g2) {
		
		if(miniMapOn == true) {
			int width = 200;
			int height = 200;
			int x = quarto.larguraTela - width - 50;
			int y = 50;
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(worldMap[quarto.currentMap], x, y, width, height, null);
			
			// DRAW PLAYER
			double scale = (double) (quarto.tileSize * quarto.maxWorldCollum)/width;
			int playerX = (int)(x + quarto.player.mundoX/scale);
			int playerY = (int)(y + quarto.player.mundoY/scale);
			int playerSize = (int)(quarto.tileSize/3.5);
			g2.drawImage(quarto.player.down1, playerX-6, playerY-6, playerSize, playerSize, null);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
}








