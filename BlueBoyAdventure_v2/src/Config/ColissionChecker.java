/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import Caracters.Entity;
import Screens.Quarto;

/**
 *
 * @author Leozin
 */
public class ColissionChecker {
    
    Quarto quarto;
    
    public ColissionChecker(Quarto quarto){
        
        this.quarto = quarto;
    }
    
    public void checkTile(Entity entity){
        
        int entityLeftWorldX = entity.mundoX + entity.solidArea.x;
        int entityRightWorldX = entity.mundoX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.mundoY + entity.solidArea.y;
        int entityBottomWorldY = entity.mundoY + entity.solidArea.y + entity.solidArea.height;
        
        int entityLeftCol = entityLeftWorldX/quarto.tileSize;   
        int entityRightCol = entityRightWorldX/quarto.tileSize;
        int entityTopRow = entityTopWorldY/quarto.tileSize;
        int entityBottomRow = entityBottomWorldY/quarto.tileSize;
        
        int tileNum1, tileNum2;
        
        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/quarto.tileSize;
                tileNum1 = quarto.tileM.mapTileNum[quarto.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = quarto.tileM.mapTileNum[quarto.currentMap][entityRightCol][entityTopRow];
                if(quarto.tileM.tile[tileNum1].colission == true || quarto.tileM.tile[tileNum2].colission == true){
                    
                    entity.colissionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/quarto.tileSize;
                tileNum1 = quarto.tileM.mapTileNum[quarto.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = quarto.tileM.mapTileNum[quarto.currentMap][entityRightCol][entityBottomRow];
                if(quarto.tileM.tile[tileNum1].colission == true || quarto.tileM.tile[tileNum2].colission == true){
                    
                    entity.colissionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/quarto.tileSize;
                tileNum1 = quarto.tileM.mapTileNum[quarto.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = quarto.tileM.mapTileNum[quarto.currentMap][entityLeftCol][entityBottomRow];
                if(quarto.tileM.tile[tileNum1].colission == true || quarto.tileM.tile[tileNum2].colission == true){
                    
                    entity.colissionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/quarto.tileSize;
                tileNum1 = quarto.tileM.mapTileNum[quarto.currentMap][entityRightCol][entityTopRow];
                tileNum2 = quarto.tileM.mapTileNum[quarto.currentMap][entityRightCol][entityBottomRow];
                if(quarto.tileM.tile[tileNum1].colission == true || quarto.tileM.tile[tileNum2].colission == true){
                    
                    entity.colissionOn = true;
                }
                break;
                
        }
    }
    
    public int checkObject(Entity entity, boolean player) {
    	
    	int index = 999;
    	
    	for(int i = 0; i < quarto.obj[1].length; i++) {
    		
    		if(quarto.obj[quarto.currentMap][i] != null) {
    			
    			// PEGAR A POSIÇÃO DA SOLID AREA DA ENTIDADE
    			entity.solidArea.x = entity.mundoX + entity.solidArea.x;
    			entity.solidArea.y = entity.mundoY + entity.solidArea.y;
    			
    			// PEGAR A POSIÇÃO DA SOLID AREA DOS OBJETOS
    			quarto.obj[quarto.currentMap][i].solidArea.x = quarto.obj[quarto.currentMap][i].mundoX + quarto.obj[quarto.currentMap][i].solidArea.x;
    			quarto.obj[quarto.currentMap][i].solidArea.y = quarto.obj[quarto.currentMap][i].mundoY + quarto.obj[quarto.currentMap][i].solidArea.y;
    			
    			
    			switch(entity.direction) {
    			case "up": entity.solidArea.y -= entity.speed; break;
    			case "down": entity.solidArea.y += entity.speed; break;
    			case "left": entity.solidArea.x -= entity.speed; break;
    			case "right": entity.solidArea.x += entity.speed; break;
    			}
    			if(entity.solidArea.intersects(quarto.obj[quarto.currentMap][i].solidArea)) {
					if(quarto.obj[quarto.currentMap][i].collision == true) {
						entity.colissionOn = true;
					}
					if(player == true) {
						index = i;
					}
				}
    			
    			entity.solidArea.x = entity.solidAreaDefaultX;
    			entity.solidArea.y = entity.solidAreaDefaultY;
    			quarto.obj[quarto.currentMap][i].solidArea.x = quarto.obj[quarto.currentMap][i].solidAreaDefaultX;
    			quarto.obj[quarto.currentMap][i].solidArea.y = quarto.obj[quarto.currentMap][i].solidAreaDefaultY;
    		}
    	} 
    	
    	return index;
    }
    
    // COLISÃO COM NPC'S OU MONSTROS
    public int checkEntity(Entity entity, Entity[][] target) {
    	
    	int index = 999;
    	
    	for(int i = 0; i < target[1].length; i++) {
    		
    		if(target[quarto.currentMap][i] != null) {
    			
    			// PEGAR A POSIÇÃO DA SOLID AREA DA ENTIDADE
    			entity.solidArea.x = entity.mundoX + entity.solidArea.x;
    			entity.solidArea.y = entity.mundoY + entity.solidArea.y;
    			
    			// PEGAR A POSIÇÃO DA SOLID AREA DOS OBJETOS
    			target[quarto.currentMap][i].solidArea.x = target[quarto.currentMap][i].mundoX + target[quarto.currentMap][i].solidArea.x;
    			target[quarto.currentMap][i].solidArea.y = target[quarto.currentMap][i].mundoY + target[quarto.currentMap][i].solidArea.y;
    			
    			
    			switch(entity.direction) {
    			case "up": entity.solidArea.y -= entity.speed; break;
    			case "down": entity.solidArea.y += entity.speed; break;
    			case "left": entity.solidArea.x -= entity.speed; break;
    			case "right": entity.solidArea.x += entity.speed; break;
    			}
    			if(entity.solidArea.intersects(target[quarto.currentMap][i].solidArea)) {
    				if(target[quarto.currentMap][i] != entity) {
    					entity.colissionOn = true;
    					index = i;	
    				}
    			}
    			
    			entity.solidArea.x = entity.solidAreaDefaultX;
    			entity.solidArea.y = entity.solidAreaDefaultY;
    			target[quarto.currentMap][i].solidArea.x = target[quarto.currentMap][i].solidAreaDefaultX;
    			target[quarto.currentMap][i].solidArea.y = target[quarto.currentMap][i].solidAreaDefaultY;
    		}
    	} 
    	
    	
    	
    	return index;
    }
    
    public boolean checkPlayer(Entity entity) {
    	
    	boolean contactPlayer = false;
    	
    	// PEGAR A POSIÇÃO DA SOLID AREA DA ENTIDADE
		entity.solidArea.x = entity.mundoX + entity.solidArea.x;
		entity.solidArea.y = entity.mundoY + entity.solidArea.y;
		
		// PEGAR A POSIÇÃO DA SOLID AREA DOS OBJETOS
		quarto.player.solidArea.x = quarto.player.mundoX + quarto.player.solidArea.x;
		quarto.player.solidArea.y = quarto.player.mundoY + quarto.player.solidArea.y;
		
		
		switch(entity.direction) {
		case "up": entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left": entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
		}
		if(entity.solidArea.intersects(quarto.player.solidArea)) {
			entity.colissionOn = true;
			contactPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		quarto.player.solidArea.x = quarto.player.solidAreaDefaultX;
		quarto.player.solidArea.y = quarto.player.solidAreaDefaultY;
		
		return contactPlayer;
    }
    
}
