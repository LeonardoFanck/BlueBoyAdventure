package Config;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Caracters.Entity;
import Screens.Quarto;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;

public class UI {

	Quarto quarto;
	Graphics2D g2;
	Font purisaB, maruMonica;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNumber = 0;
	public int titleScreenState = 0; // 0: primeira tela, 1: segunda tela
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0;
	int counter = 0;
	public Entity npc;
	
	
	public UI(Quarto quarto) {
		
		this.quarto = quarto;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// CRIA O HUD DE OBJETOS
		Entity heart = new OBJ_Heart(quarto);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(quarto);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity bronzeCoin = new OBJ_Coin_Bronze(quarto);
		coin = bronzeCoin.down1;
	}
	
	public void addMessage(String text) {
 		
		message.add(text);
		messageCounter.add(0);
	}
	
	public void draw(Graphics2D g2) {

		this.g2 = g2;
		
		g2.setFont(maruMonica);
		//g2.setFont(purisaB);
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(quarto.gameState == quarto.titleState) {
			drawTitleScreen();
		}
		
		// PLAY STATE
		if(quarto.gameState == quarto.playState) {
			drawPlayerLife();
			drawMessage();
		}
		// PAUSE STATE
		if(quarto.gameState == quarto.pauseState) {
			drawPlayerLife();
			drawPauseScreem();
		}
		// DIALOGUE STATE
		if(quarto.gameState == quarto.dialogueState) {
			drawDialogueScreen();
		}
		
		// CARACTER STATE
		if(quarto.gameState == quarto.caracterState) {
			drawCaracterScreen();
			drawInventory(quarto.player, true);
		}
		
		// OPTION STATE
		if(quarto.gameState == quarto.optionState) {
			drawOptionScreen();
		}
		
		// GAME OVER STATE
		if(quarto.gameState == quarto.gameOverState) {
			drawGameOverScreen();
		}
		
		// TRANSICTION STATE
		if(quarto.gameState == quarto.transitionState) {
			drawTransition();
		}
		
		// TRADE STATE
		if(quarto.gameState == quarto.tradeState) {
			drawTradeScreen();
		}
		
		// SLEEP STATE
		if(quarto.gameState == quarto.sleepState) {
			drawSleepScreen();
		}
	}
	
	public void drawPlayerLife() {
		
		int x = quarto.tileSize/2;
		int y = quarto.tileSize/2;
		int i = 0;
		
		// DRAW VIDA MÁXIMA
		while(i < quarto.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += quarto.tileSize;
		}
		
		// RESETAR OS VALORES
		x = quarto.tileSize/2;
		y = quarto.tileSize/2;
		i = 0;
		
		// DRAW VIDA ATUAL
		while(i < quarto.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < quarto.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += quarto.tileSize;
		}
		
		// DESENHAR A MANA MAXIMA
		x = (quarto.tileSize/2) - 5;
		y = (int) (quarto.tileSize*1.5);
		i = 0;
		while(i < quarto.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 35;
		}
		
		// DESENHAR A MANA
		x = (quarto.tileSize/2) - 5;
		y = (int) (quarto.tileSize*1.5);
		i = 0;
		while(i < quarto.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 35;
		}
	}
	
	public void drawMessage() {
		
		int messageX = quarto.tileSize;
		int messageY = quarto.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1; // messageCounter++
				messageCounter.set(i, counter); // COLOCANDO A CONTAGEM NO ARRAY
				messageY += 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, quarto.larguraTela, quarto.alturaTela);
			
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String text = "The Cat Adventure";
			int x = getXForCenteredText(text);
			int y = quarto.tileSize*3;
			
			// SOMBRA
			g2.setColor(Color.gray);
			g2.drawString(text, x+5, y+5);
			
			// COR PRINCIPAL
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			// IMAGEM DO player
			x = quarto.larguraTela/2 - (quarto.tileSize*2)/2;
			y += quarto.tileSize*2;
			g2.drawImage(quarto.player.down1, x, y, quarto.tileSize*2, quarto.tileSize*2, null);
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			
			text = "NOVO JOGO";
			x = getXForCenteredText(text);
			y += quarto.tileSize*3.5;
			g2.drawString(text, x, y);
			if(commandNumber == 0) {
				g2.drawString(">", x-quarto.tileSize, y);
			}
			
			text = "CARREGAR JOGO";
			x = getXForCenteredText(text);
			y += quarto.tileSize;
			g2.drawString(text, x, y);
			if(commandNumber == 1) {
				g2.drawString(">", x-quarto.tileSize, y);
			}
			
			text = "SAIR";
			x = getXForCenteredText(text);
			y += quarto.tileSize;
			g2.drawString(text, x, y);
			if(commandNumber == 2) {
				g2.drawString(">", x-quarto.tileSize, y);
			} 
		}
		else if(titleScreenState == 1) {
			
			// TELA DE SELEÇÃO DE CLASSE
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Selecione sua classe!";
			int x = getXForCenteredText(text);
			int y = quarto.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Lutador";
			x = getXForCenteredText(text);
			y += quarto.tileSize*3;
			g2.drawString(text, x, y);
			if(commandNumber == 0) {
				g2.drawString(">", x-quarto.tileSize, y);
			}
			
			text = "Ladrão";
			x = getXForCenteredText(text);
			y += quarto.tileSize;
			g2.drawString(text, x, y);
			if(commandNumber == 1) {
				g2.drawString(">", x-quarto.tileSize, y);
			}
			
			text = "Mago";
			x = getXForCenteredText(text);
			y += quarto.tileSize;
			g2.drawString(text, x, y);
			if(commandNumber == 2) {
				g2.drawString(">", x-quarto.tileSize, y);
			}
			
			text = "Voltar";
			x = getXForCenteredText(text);
			y += quarto.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNumber == 3) {
				g2.drawString(">", x-quarto.tileSize, y);
			}
		}
		
	}
	
	public void drawPauseScreem() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSADO";
		int x = getXForCenteredText(text);
		
		int y = quarto.alturaTela/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		
		// WINDOW
		int x = quarto.tileSize*3;
		int y = quarto.tileSize/2;
		int width = quarto.larguraTela - (quarto.tileSize*6);
		int height = quarto.tileSize*4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
		x += quarto.tileSize;
		y += quarto.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	
	public void drawCaracterScreen() {
		
		// CRIANDO UM FRAME
		final int frameX = quarto.tileSize*2;
		final int frameY = quarto.tileSize;
		final int frameWidth = quarto.tileSize * 5;
		final int frameHeight = quarto.tileSize * 10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXTO
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + 20;
		int textY = frameY + quarto.tileSize;
		final int lineHeight = 35;
		
		// NOMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Vida", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Força", textX, textY);
		textY += lineHeight;
		g2.drawString("Destresa", textX, textY);
		textY += lineHeight;
		g2.drawString("Ataque", textX, textY);
		textY += lineHeight;
		g2.drawString("Defesa", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Proximo Level:", textX, textY);
		textY += lineHeight;
		g2.drawString("Moedas", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Arma", textX, textY);
		textY += lineHeight + 15;
		g2.drawString("Escudo", textX, textY);
		
		// VALORES
		int tailX = (frameX + frameWidth) - 30;
		// RESENTANDO O textY
		textY = frameY + quarto.tileSize;
		String value;
		
		value = String.valueOf(quarto.player.level);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.life + "/" + quarto.player.maxLife);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.mana + "/" + quarto.player.maxMana);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.strength);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.dexterity);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.attack);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.defense);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.exp);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.nextLevelExp);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(quarto.player.coin);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		g2.drawImage(quarto.player.currentWeapon.down1, tailX - quarto.tileSize, textY - 24, null);
		textY += quarto.tileSize;
		
		g2.drawImage(quarto.player.currentShield.down1, tailX - quarto.tileSize, textY - 24, null);
	}
	
	public void drawInventory(Entity entity, boolean cursor) {
		
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		
		if(entity == quarto.player) {
			frameX = quarto.tileSize * 12;
			frameY = quarto.tileSize;
			frameWidth = quarto.tileSize * 6;
			frameHeight = quarto.tileSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = quarto.tileSize * 2;
			frameY = quarto.tileSize;
			frameWidth = quarto.tileSize * 6;
			frameHeight = quarto.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		// FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// ESPAÇOS DE ARMAZENAMENTO
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = quarto.tileSize + 3;
		
		// DESENHANDO ITENS DO PLAYER
		for(int i = 0; i < entity.inventory.size(); i++) {
			
			// CURSOR PARA EQUIPAR ITENS
			if(entity.inventory.get(i) == entity.currentWeapon ||
					entity.inventory.get(i) == entity.currentShield ||
					entity.inventory.get(i)  == entity.currentLight) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, quarto.tileSize, quarto.tileSize, 10, 10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			
			// DESENHA A QUANTIDADE DO MESMO ITEM (AMOUNT)
			if(entity == quarto.player && entity.inventory.get(i).amount > 1) {
				g2.setFont(g2.getFont().deriveFont(32f));
				int amountX;
				int amountY;
				
				String s = "" + entity.inventory.get(i).amount;
				amountX = getXForAlignToRightText(s, slotX + 44);
				amountY = slotY + quarto.tileSize;
				
				// SOMBRA 
				g2.setColor(new Color(60, 60, 60));
				g2.drawString(s, amountX, amountY);
				
				// NÚMERO
				g2.setColor(Color.white);
				g2.drawString(s, amountX-3, amountY-3);
			}
			
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += slotSize;
			}
		}
		
		// CURSOR
		
		if(cursor == true) {
			int cursorX = slotXStart + (slotSize * slotCol);
			int cursorY = slotYStart + (slotSize * slotRow);
			int cursorWidth = quarto.tileSize;
			int cursorHeight = quarto.tileSize;
			
			// DESENHANDO O CURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			// FRAME DAS DESCRIÇÕES
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = quarto.tileSize * 3;
			
			// DESENHANDO O TEXTO DA DESCRIÇÃO
			int textX = dFrameX + 20;
			int textY = dFrameY + quarto.tileSize;
			g2.setFont(g2.getFont().deriveFont(28F));
			
			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
			
			if(itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				
				for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);	
					textY += 32;
				}
			}
		}
	}
	
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, quarto.larguraTela, quarto.alturaTela);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		text = "Fim de Jogo";
		
		// SOMBRA
		g2.setColor(Color.black);
		x = getXForCenteredText(text);
		y = quarto.tileSize*4;
		g2.drawString(text, x, y);
		// PRINCIPAL
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// RECOMEÇAR
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Recomeçar";
		x = getXForCenteredText(text);
		y += quarto.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNumber == 0) {
			g2.drawString(">", x-40, y);
		}
		
		// VOLTAR PARA A TELA INICIAL
		text = "Desistir";
		x = getXForCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNumber == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	
	public void drawOptionScreen() {
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		// SUB WINDOW
		int frameX = quarto.tileSize*6;
		int frameY = quarto.tileSize;
		int frameWidth = quarto.tileSize*8;
		int frameHeight = quarto.tileSize*10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: optionsTop(frameX, frameY); break;
		case 1: optionsFullScreenNotification(frameX, frameY); break;
		case 2: optionsControl(frameX, frameY); break;
		case 3: optionsEndGameConfirmation(frameX, frameY);
		}
		
		quarto.keyH.enterPressed = false;
	}
	
	public void optionsTop(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		String text = "Opções";
		textX = getXForCenteredText(text);
		textY = frameY + quarto.tileSize;
		g2.drawString(text, textX, textY);
		
		// TELA CHEIA (LIGAR/DESLIGAR)
		textX = frameX + quarto.tileSize;
		textY += quarto.tileSize*2;
		g2.drawString("Tela Cheia", textX, textY);
		if(commandNumber == 0) {
			g2.drawString(">", textX-25, textY);
			if(quarto.keyH.enterPressed == true) {
				if(quarto.fullScreenOn == false) {
					quarto.fullScreenOn = true;
				}
				else if(quarto.fullScreenOn == true) {
					quarto.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		
		// MUSICA
		textY += quarto.tileSize;
		g2.drawString("Musica", textX, textY);
		if(commandNumber == 1) {
			g2.drawString(">", textX-25, textY);
		}
		
		// SE
		textY += quarto.tileSize;
		g2.drawString("SOM", textX, textY);
		if(commandNumber == 2) {
			g2.drawString(">", textX-25, textY);
		}
		
		// CONTROLES
		textY += quarto.tileSize;
		g2.drawString("Controles", textX, textY);
		if(commandNumber == 3) {
			g2.drawString(">", textX-25, textY);
			if(quarto.keyH.enterPressed == true) {
				subState = 2;
				commandNumber = 0;
			}
		}
		
		// FIM DE JOGO
		textY += quarto.tileSize;
		g2.drawString("Sair do Jogo", textX, textY);
		if(commandNumber == 4) {
			g2.drawString(">", textX-25, textY);
			if(quarto.keyH.enterPressed == true) {
				subState = 3;
				commandNumber = 0;
			}
		}
		
		// VOLTAR
		textX = getXForCenteredText(text);
		textY += quarto.tileSize*2;
		g2.drawString("Voltar", textX, textY);
		if(commandNumber == 5) {
			g2.drawString(">", textX-25, textY);
			if(quarto.keyH.enterPressed == true) {
				quarto.gameState = quarto.playState;
				commandNumber = 0;
			}
		}

		// CAIXA VERIFICADORA DA TELA CHEIA
		textX = frameX + (int) (quarto.tileSize*4.5);
		textY = frameY + quarto.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3)); // AFINA A BORDA DO QUADRADO 
		g2.drawRect(textX, textY, 24, 24);
		if(quarto.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		// VOLUME DA MUSICA
		textY += quarto.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		int volumeWidth = 24 * quarto.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		// VOLUME DO SOM
		textY += quarto.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * quarto.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		quarto.config.saveConfig();
	}
	
	public void optionsControl(int frameX, int frameY) {
		
		int textX;
		int textY;
		 
		// TÍTULO
		String text = "Controles";
		textX = getXForCenteredText(text);
		textY = frameY + quarto.tileSize;
		g2.drawString(text, textX, textY);
		
		// PRIMEIRO TEXTO
		textX = frameX + quarto.tileSize;
		textY += quarto.tileSize;
		
		g2.drawString("Mover", textX, textY); 
		textY += quarto.tileSize;
		g2.drawString("Confirmar/Atacar", textX, textY); 
		textY += quarto.tileSize;
		g2.drawString("Atirar", textX, textY); 
		textY += quarto.tileSize;		
		g2.drawString("Inventário", textX, textY); 
		textY += quarto.tileSize;		
		g2.drawString("Pause", textX, textY); 
		textY += quarto.tileSize;		
		g2.drawString("Opções", textX, textY); 
		textY += quarto.tileSize;
		
		// SEGUNDO TEXTO
		textX = frameX + quarto.tileSize*6;
		textY = frameY + quarto.tileSize*2;
		
		g2.drawString("WASD", textX, textY);
		textY += quarto.tileSize;
		g2.drawString("ENTER", textX, textY);
		textY += quarto.tileSize;
		g2.drawString("F", textX, textY);
		textY += quarto.tileSize;
		g2.drawString("C", textX, textY);
		textY += quarto.tileSize;
		g2.drawString("P", textX, textY);
		textY += quarto.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += quarto.tileSize;
		
		// VOLTAR
		textX = frameX + quarto.tileSize;
		textY = frameY + quarto.tileSize*9;
		g2.drawString("Voltar", textX, textY);
		if(commandNumber == 0) {
			g2.drawString(">", textX-25, textY);
			if(quarto.keyH.enterPressed == true) {
				subState = 0;
				commandNumber = 3;
			}
		}
	}
	
	public void optionsEndGameConfirmation(int frameX, int frameY) {
		
		int textX = frameX + quarto.tileSize;
		int textY = frameY + quarto.tileSize*3;
		
		currentDialogue = "Sair do jogo e \nvoltar para o início?";
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// OPÇÃO SIM
		String text = "Sim";
		textX = getXForCenteredText(text);
		textY += quarto.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNumber == 0) {
			g2.drawString(">", textX-25, textY);
			if(quarto.keyH.enterPressed == true) {
				subState = 0;
				titleScreenState = 0;
				quarto.gameState = quarto.titleState;
			}
		}
		
		// OPÇÃO NÃO
		text = "Não";
		textX = getXForCenteredText(text);
		textY += quarto.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNumber == 1) {
			g2.drawString(">", textX-25, textY);
			if(quarto.keyH.enterPressed == true) {
				subState = 0;
				commandNumber = 4;
			}
		}
	}
	
	public void drawTransition() {
		
		counter++;
		g2.setColor(new Color(0, 0, 0, counter*5));
		g2.fillRect(0, 0, quarto.larguraTela, quarto.alturaTela);
	
		if(counter == 50) {
			counter = 0;
			quarto.gameState = quarto.playState;
			quarto.currentMap = quarto.eHandler.tempMap;
			quarto.player.mundoX = quarto.tileSize * quarto.eHandler.tempCol;
			quarto.player.mundoY = quarto.tileSize * quarto.eHandler.tempRow;
			quarto.eHandler.previousEventX = quarto.player.mundoX;
			quarto.eHandler.previousEventY = quarto.player.mundoY;
		}
	}
	
	public void drawTradeScreen() {
		
		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		
		quarto.keyH.enterPressed = false;
	}
	
	public void trade_select() {
		
		drawDialogueScreen();
		
		// DESENHANDO A TELA
		int x = quarto.tileSize * 15;
		int y = quarto.tileSize * 4;
		int width = (int) (quarto.tileSize * 3.5);
		int height = (int) (quarto.tileSize * 3.5);
		drawSubWindow(x, y, width, height);
		
		// DESENHANDO OS TEXTOS
		x += quarto.tileSize;
		y += quarto.tileSize;
		
		g2.drawString("Compar", x, y);
		if(commandNumber == 0) {
			g2.drawString(">", x-24, y);
			if(quarto.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y += quarto.tileSize;
		
		g2.drawString("Vender", x, y);
		if(commandNumber == 1) {
			g2.drawString(">", x-24, y);
			if(quarto.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		y += quarto.tileSize;
		
		g2.drawString("Sair", x, y);
		if(commandNumber == 2) {
			g2.drawString(">", x-24, y);
			if(quarto.keyH.enterPressed == true) {
				commandNumber = 0;
				quarto.gameState = quarto.dialogueState;
				currentDialogue = "Volte logo, estarei esperando...";
			}
		}
	}
	
	public void trade_buy() {
		
		// DESENHANDO INVENTARIO DO JOGADOR
		drawInventory(quarto.player, false);
		drawInventory(npc, true);
		
		// DESENHANDO O TEXTO
		int x = quarto.tileSize * 2;
		int y = quarto.tileSize * 9;
		int width = quarto.tileSize * 6;
		int height = quarto.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Voltar", x+24, y+60);
		
		// DESENHANDO AS MOEDAS DO JOGADOR
		x = quarto.tileSize * 12;
		y = quarto.tileSize * 9;
		width = quarto.tileSize * 6;
		height = quarto.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Suas moedas: " + quarto.player.coin, x+24, y+60);
		
		// DESENHANDO A JANELA DE PREÇO
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			x = (int) (quarto.tileSize * 5.5);
			y = (int) (quarto.tileSize * 5.5);
			width = (int) (quarto.tileSize * 2.5);
			height = quarto.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+8, 32, 32, null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXForAlignToRightText(text, quarto.tileSize*8-20);
			g2.drawString(text, x, y+34);
			
			// COMPRANDO ITEM
			if(quarto.keyH.enterPressed == true) {
				if(npc.inventory.get(itemIndex).price > quarto.player.coin) {
					subState = 0;
					quarto.gameState = quarto.dialogueState;
					currentDialogue = "Você não está tentando me passar a perna né?";
					drawDialogueScreen();
				}
				else {
					if(quarto.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						quarto.player.coin -= npc.inventory.get(itemIndex).price;
					}
					else {
						subState = 0;
						quarto.gameState = quarto.dialogueState;
						currentDialogue = "A ganância não irá o levar a lugar algum..";
					}
				}
			}
		}
	}
	
	public void trade_sell() {
		
		// DESENHANDO INVENTARIO DO JOGADOR
		drawInventory(quarto.player, true);
		
		int x;
		int y;
		int width;
		int height;
		
		// DESENHANDO O TEXTO
		x = quarto.tileSize * 2;
		y = quarto.tileSize * 9;
		width = quarto.tileSize * 6;
		height = quarto.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Voltar", x+24, y+60);
		
		// DESENHANDO AS MOEDAS DO JOGADOR
		x = quarto.tileSize * 12;
		y = quarto.tileSize * 9;
		width = quarto.tileSize * 6;
		height = quarto.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Suas moedas: " + quarto.player.coin, x+24, y+60);
		
		// DESENHANDO A JANELA DE PREÇO
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if(itemIndex < quarto.player.inventory.size()) {
			x = (int) (quarto.tileSize * 15.5);
			y = (int) (quarto.tileSize * 5.5);
			width = (int) (quarto.tileSize * 2.5);
			height = quarto.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+8, 32, 32, null);
			
			int price = quarto.player.inventory.get(itemIndex).price/2;
			String text = "" + price;
			x = getXForAlignToRightText(text, quarto.tileSize*18-20);
			g2.drawString(text, x, y+34);
			
			// VENDENDO ITEM
			if(quarto.keyH.enterPressed == true) {
				if(quarto.player.inventory.get(itemIndex) == quarto.player.currentWeapon || 
						quarto.player.inventory.get(itemIndex) == quarto.player.currentShield) {
					commandNumber = 0;
					subState = 0;
					quarto.gameState = quarto.dialogueState;
					currentDialogue = "Você não acha que está usando isso?";
				}
				else {
					if(quarto.player.inventory.get(itemIndex).amount > 1) {
						quarto.player.inventory.get(itemIndex).amount--;
					}
					else {
						quarto.player.inventory.remove(itemIndex);
					}

					quarto.player.coin += price;
				}
			}
		}
	}
	
	public void drawSleepScreen() {
		
		counter++;
		
		if(counter < 120) {
			quarto.eManager.lighting.filterAlpha += 0.01f;
			if(quarto.eManager.lighting.filterAlpha > 1f) {
				quarto.eManager.lighting.filterAlpha = 1f;
			}
		}
		if(counter >= 120) {
			quarto.eManager.lighting.filterAlpha -= 0.01f;
			if(quarto.eManager.lighting.filterAlpha <= 0f) {
				quarto.eManager.lighting.filterAlpha = 0f;
				counter = 0;
				quarto.eManager.lighting.dayState = quarto.eManager.lighting.day;
				quarto.eManager.lighting.dayCounter = 0;
				quarto.gameState = quarto.playState;
				quarto.player.getplayerImage(); // DEVOLVENDO A IMAGEM DO PLAYER (ERA TENDA)
			}
		}
	}
	
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		
		int itemIndex = slotCol + (slotRow * 5);
		
		return itemIndex;
	}
	
	public void optionsFullScreenNotification(int frameX, int frameY) {
		
		int textX = frameX + quarto.tileSize;
		int textY = frameY + quarto.tileSize*3;
		
		currentDialogue = "Para afetuar a alteração \né necessário reiniciar \no jogo!";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// VOLTAR
		textY = frameY + quarto.tileSize*9;
		g2.drawString("Voltar", textX, textY); 
		if(commandNumber == 0) {
			g2.drawString(">", textX-25, textY);
			if(quarto.keyH.enterPressed == true) {
				subState = 0; 
			}
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	
	public int getXForCenteredText(String text) {
		
		int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = quarto.larguraTela/2 - lenght/2;
		
		return x;
	}
	
	public int getXForAlignToRightText(String text, int tailX) {
		
		int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - lenght;
		
		return x;
	}
	
}
