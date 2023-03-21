package Config;

import Caracters.Entity;
import Screens.Quarto;

public class EventHandler {

	Quarto quarto;
	EventRect eventRect[][][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(Quarto quarto) {
		
		this.quarto = quarto;
		
		eventRect = new EventRect[quarto.maxMap][quarto.maxWorldCollum][quarto.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while(map < quarto.maxMap && col < quarto.maxWorldCollum && row < quarto.maxWorldRow) {
			
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height =2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if(col == quarto.maxWorldCollum) {
				col = 0;
				row++;
				
				if(row == quarto.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
	}
	
	public void checkEvent() {
		
		// VERIFICA SE O player ESTÁ A UM TILE DE DISTANCIA DO ULTIMO EVENTO
		int xDistance = Math.abs(quarto.player.mundoX - previousEventX);
		int yDistance = Math.abs(quarto.player.mundoY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > quarto.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			// MAPA 0 - CAMPO
			if(hit(0, 27, 16, "right") == true) { damagePit(quarto.dialogueState);} // EVENTO ACONTECE
			else if(hit(0, 23, 19, "any") == true) { damagePit(quarto.dialogueState);}
			else if(hit(0, 23, 12, "up") == true) { healingPool(quarto.dialogueState);}
			else if(hit(0, 10, 39, "any") == true) { teleport(1, 12, 13);}
			else if(hit(1, 12, 13, "any") == true) { teleport(0, 10, 39);}
			else if(hit(1, 12, 9, "up") == true) { speak(quarto.npc[1][0]);}
		}
		
		
	}
	
	public boolean hit(int map, int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		if(map == quarto.currentMap) {
			quarto.player.solidArea.x = quarto.player.mundoX + quarto.player.solidArea.x;
			quarto.player.solidArea.y = quarto.player.mundoY + quarto.player.solidArea.y;
			eventRect[map][col][row].x = col*quarto.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*quarto.tileSize + eventRect[map][col][row].y;
			
			if(quarto.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
				if(quarto.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;
					
					previousEventX = quarto.player.mundoX;
					previousEventY = quarto.player.mundoY;
				}
			}
			
			quarto.player.solidArea.x = quarto.player.solidAreaDefaultX;
			quarto.player.solidArea.y = quarto.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		
		return hit;
	}
	
	public void damagePit(int gameState) {
		
		quarto.gameState = gameState;
		quarto.ui.currentDialogue = "Você caiu em um buraco!";
		quarto.player.life -= 1;
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healingPool(int gameState) {
		
		if(quarto.keyH.enterPressed == true) {
			quarto.gameState = gameState;
			quarto.player.attackCanceled = true;
			quarto.ui.currentDialogue = "Voce se curou com a água sagrada\n Sua vida e mana foram restaurada";
			quarto.player.life = quarto.player.maxLife;
			quarto.player.mana = quarto.player.maxMana;
			quarto.aSetter.setMonster();
		}
	}
	
	public void teleport(int map, int col, int row) {
		
		quarto.gameState = quarto.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row; 
		canTouchEvent = false;
		quarto.playSE(12);
	}
	
	public void speak(Entity entity) {
		
		if(quarto.keyH.enterPressed == true) {
			quarto.gameState = quarto.dialogueState;
			quarto.player.attackCanceled = true;
			entity.speak();
		}
	}
}
