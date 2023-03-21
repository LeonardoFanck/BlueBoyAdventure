package Config;

import Caracters.NPC_Merchant;
import Caracters.NPC_OldMan;
import Screens.Quarto;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion;
import object.OBJ_Shield;
import object.OBJ_Sword_Normal;
import tileInteractive.IT_DryTree;

public class AssetSetter {

	Quarto quarto;
	
	public AssetSetter(Quarto quarto) {
		this.quarto = quarto;
	}
	
	public void setObject() {
		
		int mapNum = 0;
		int i = 0;
		
		quarto.obj[mapNum][i] = new OBJ_Door(quarto);
		quarto.obj[mapNum][i].mundoX = quarto.tileSize*14;
		quarto.obj[mapNum][i].mundoY = quarto.tileSize*28;
		i++;
		
		quarto.obj[mapNum][i] = new OBJ_Door(quarto);
		quarto.obj[mapNum][i].mundoX = quarto.tileSize*12;
		quarto.obj[mapNum][i].mundoY = quarto.tileSize*12;
		i++;
		
		quarto.obj[mapNum][i] = new OBJ_Chest(quarto, new OBJ_Key(quarto));
		quarto.obj[mapNum][i].mundoX = quarto.tileSize*22;
		quarto.obj[mapNum][i].mundoY = quarto.tileSize*23;
		i++;
		
//		quarto.obj[mapNum][i] = new OBJ_Coin_Bronze(quarto);
//		quarto.obj[mapNum][i].mundoX = quarto.tileSize*25;
//		quarto.obj[mapNum][i].mundoY = quarto.tileSize*23;
//		i++;
//		
//		quarto.obj[mapNum][i] = new OBJ_Coin_Bronze(quarto);
//		quarto.obj[mapNum][i].mundoX = quarto.tileSize*21;
//		quarto.obj[mapNum][i].mundoY = quarto.tileSize*19;
//		i++;
//		
//		quarto.obj[mapNum][i] = new OBJ_Coin_Bronze(quarto);
//		quarto.obj[mapNum][i].mundoX = quarto.tileSize*21;
//		quarto.obj[mapNum][i].mundoY = quarto.tileSize*21;
//		i++;
//		
//		quarto.obj[mapNum][i] = new OBJ_Axe(quarto);
//		quarto.obj[mapNum][i].mundoX = quarto.tileSize*33;
//		quarto.obj[mapNum][i].mundoY = quarto.tileSize*21;
//		i++;
//		
//		quarto.obj[mapNum][i] = new OBJ_Shield(quarto);
//		quarto.obj[mapNum][i].mundoX = quarto.tileSize*35;
//		quarto.obj[mapNum][i].mundoY = quarto.tileSize*21;
//		i++;
//		
		quarto.obj[mapNum][i] = new OBJ_Potion(quarto);
		quarto.obj[mapNum][i].mundoX = quarto.tileSize*22;
		quarto.obj[mapNum][i].mundoY = quarto.tileSize*27;
		i++;
		
		quarto.obj[mapNum][i] = new OBJ_Potion(quarto);
		quarto.obj[mapNum][i].mundoX = quarto.tileSize*22;
		quarto.obj[mapNum][i].mundoY = quarto.tileSize*28;
		i++;
		
		quarto.obj[mapNum][i] = new OBJ_Potion(quarto);
		quarto.obj[mapNum][i].mundoX = quarto.tileSize*22;
		quarto.obj[mapNum][i].mundoY = quarto.tileSize*29;
		i++;
		
		quarto.obj[mapNum][i] = new OBJ_Potion(quarto);
		quarto.obj[mapNum][i].mundoX = quarto.tileSize*22;
		quarto.obj[mapNum][i].mundoY = quarto.tileSize*30;
		i++;
		
		quarto.obj[mapNum][i] = new OBJ_Lantern(quarto);
		quarto.obj[mapNum][i].mundoX = quarto.tileSize*18;
		quarto.obj[mapNum][i].mundoY = quarto.tileSize*20;
		i++;	
//		
//		quarto.obj[mapNum][i] = new OBJ_Heart(quarto);
//		quarto.obj[mapNum][i].mundoX = quarto.tileSize*22;
//		quarto.obj[mapNum][i].mundoY = quarto.tileSize*29;
//		i++;
//		
//		quarto.obj[mapNum][i] = new OBJ_ManaCrystal(quarto);
//		quarto.obj[mapNum][i].mundoX = quarto.tileSize*22;
//		quarto.obj[mapNum][i].mundoY = quarto.tileSize*31;
//		i++;
	}
	
	public void setNPC() {
		
		int mapNum = 0;
		int i = 0;
		
		// MAPA 0
		quarto.npc[mapNum][i] = new NPC_OldMan(quarto);
		quarto.npc[mapNum][i].mundoX = quarto.tileSize*21;
		quarto.npc[mapNum][i].mundoY = quarto.tileSize*21;
		i++;
		
		quarto.npc[mapNum][i] = new NPC_OldMan(quarto);
		quarto.npc[mapNum][i].mundoX = quarto.tileSize*9;
		quarto.npc[mapNum][i].mundoY = quarto.tileSize*10;
		i++;
		
		// MAPA 1
		mapNum = 1;
		i = 0;
		quarto.npc[mapNum][i] = new NPC_Merchant(quarto);
		quarto.npc[mapNum][i].mundoX = quarto.tileSize*12;
		quarto.npc[mapNum][i].mundoY = quarto.tileSize*7;
		i++;
	}
	
	public void setMonster() {
		
		int mapNum = 0;
		int i = 0;
		
		quarto.monster[mapNum][i] = new MON_GreenSlime(quarto);
		quarto.monster[mapNum][i].mundoX = quarto.tileSize*23;
		quarto.monster[mapNum][i].mundoY = quarto.tileSize*36;
		i++;
		
		quarto.monster[mapNum][i] = new MON_GreenSlime(quarto);
		quarto.monster[mapNum][i].mundoX = quarto.tileSize*23;
		quarto.monster[mapNum][i].mundoY = quarto.tileSize*37;
		i++;
//		
//		quarto.monster[mapNum][i] = new MON_GreenSlime(quarto);
//		quarto.monster[mapNum][i].mundoX = quarto.tileSize*24;
//		quarto.monster[mapNum][i].mundoY = quarto.tileSize*27;
//		i++;
//		
//		quarto.monster[mapNum][i] = new MON_GreenSlime(quarto);
//		quarto.monster[mapNum][i].mundoX = quarto.tileSize*34;
//		quarto.monster[mapNum][i].mundoY = quarto.tileSize*42;
//		i++;
//		
//		quarto.monster[mapNum][i] = new MON_GreenSlime(quarto);
//		quarto.monster[mapNum][i].mundoX = quarto.tileSize*38;
//		quarto.monster[mapNum][i].mundoY = quarto.tileSize*42;
	}
	
	public void setInteractiveTile() {
		
		int mapNum = 0;
		int i = 0;
		
		quarto.iTile[mapNum][i] = new IT_DryTree(quarto, 27, 12);
		i++;
		
		quarto.iTile[mapNum][i] = new IT_DryTree(quarto, 28, 12);
		i++;
		
		quarto.iTile[mapNum][i] = new IT_DryTree(quarto, 29, 12);
		i++;
		
		quarto.iTile[mapNum][i] = new IT_DryTree(quarto, 30, 12);
		i++;
		
		quarto.iTile[mapNum][i] = new IT_DryTree(quarto, 31, 12);
		i++;
		
		quarto.iTile[mapNum][i] = new IT_DryTree(quarto, 32, 12);
		i++;
		
		quarto.iTile[mapNum][i] = new IT_DryTree(quarto, 33, 12);
		i++;
	}
}
