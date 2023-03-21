/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tile;

import Screens.Quarto;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import Config.UtilityTool;

/**
 *
 * @author Leozin
 */
public class tileManager {
    
    Quarto quarto;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = true;
    
    public tileManager(Quarto quarto){
        
        this.quarto = quarto;
        
        tile = new Tile[50];
        mapTileNum = new int[quarto.maxMap][quarto.maxWorldCollum][quarto.maxWorldRow];
        
        getTileImage();
        loadMap("/maps/worldV3.txt", 0);
        loadMap("/maps/interior01.txt", 1);  
    }
    
    public void getTileImage(){
        
    	// PLACEHOLDER
            setup(0, "grass00", false);
            setup(1, "grass00", false);
            setup(2, "grass00", false);
            setup(3, "grass00", false);
            setup(4, "grass00", false);
            setup(5, "grass00", false);
            setup(6, "grass00", false);
            setup(7, "grass00", false);
            setup(8, "grass00", false);
            setup(9, "grass00", false);
         // PLACEHOLDER
            
            // 
            setup(10, "grass00", false);
            setup(11, "grass01", false);
            setup(12, "water00", true);
            setup(13, "water01", true);
            setup(14, "water02", true);
            setup(15, "water03", true);
            setup(16, "water04", true);
            setup(17, "water05", true);
            setup(18, "water06", true);
            setup(19, "water07", true);
            setup(20, "water08", true);
            setup(21, "water09", true);
            setup(22, "water10", true);
            setup(23, "water11", true);
            setup(24, "water12", true);
            setup(25, "water13", true);
            setup(26, "road00", false);
            setup(27, "road01", false);
            setup(28, "road02", false);
            setup(29, "road03", false);
            setup(30, "road04", false);
            setup(31, "road05", false);
            setup(32, "road06", false);
            setup(33, "road07", false);
            setup(34, "road08", false);
            setup(35, "road09", false);
            setup(36, "road10", false);
            setup(37, "road11", false);
            setup(38, "road12", false);
            setup(39, "earth", false);
            setup(40, "wall", true);
            setup(41, "tree", true);
            setup(42, "hut", false);
            setup(43, "floor01", false);
            setup(44, "table01", true);
    }
    
    public void setup(int index, String imageName, boolean colission) {
    	
    	UtilityTool uTool = new UtilityTool();
    	
    	try {
    		tile[index] = new Tile();
    		tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
    		tile[index].image = uTool.scaleImage(tile[index].image, quarto.tileSize, quarto.tileSize);
    		tile[index].colission = colission;
    	}
    	catch (IOException e) {
			e.getStackTrace();
		}
    }
    
    public void loadMap(String filePath, int map){
        
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            while(col < quarto.maxWorldCollum && row < quarto.maxWorldRow){
                
                String line = br.readLine();
                
                while(col < quarto.maxWorldCollum){
                    
                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == quarto.maxWorldCollum){
                    col = 0;
                    row++;
                }
            }
            
            br.close();
        }
        catch(Exception e){
            
        }
    }
    
    public void draw(Graphics2D g2){
        
        int worldCollun = 0;
        int worldRow = 0;
        
        while(worldCollun < quarto.maxWorldCollum && worldRow < quarto.maxWorldRow){
            
            int tileNum = mapTileNum[quarto.currentMap][worldCollun][worldRow];
            
            int worldX = worldCollun * quarto.tileSize;
            int worldY = worldRow * quarto.tileSize;
            int screenX = worldX - quarto.player.mundoX + quarto.player.screenX;
            int screenY = worldY - quarto.player.mundoY + quarto.player.screenY;
            
            if(worldX + quarto.tileSize > quarto.player.mundoX - quarto.player.screenX && worldX - quarto.tileSize < quarto.player.mundoX + quarto.player.screenX
            && worldY + quarto.tileSize > quarto.player.mundoY - quarto.player.screenY && worldY - quarto.tileSize < quarto.player.mundoY + quarto.player.screenY){
                
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            
            worldCollun++;
            
            if(worldCollun == quarto.maxWorldCollum){
                worldCollun = 0;
                worldRow++;
            }
        }
        
        if(drawPath == true) {
        	g2.setColor(new Color(255, 0, 0 , 70));
        	
        	for(int i = 0; i < quarto.pFinder.pathList.size(); i++) {
                int worldX = quarto.pFinder.pathList.get(i).col * quarto.tileSize;
                int worldY = quarto.pFinder.pathList.get(i).row * quarto.tileSize;
                int screenX = worldX - quarto.player.mundoX + quarto.player.screenX;
                int screenY = worldY - quarto.player.mundoY + quarto.player.screenY;
                
                g2.fillRect(screenX, screenY, quarto.tileSize, quarto.tileSize);
        	} 
        }
    }
}
