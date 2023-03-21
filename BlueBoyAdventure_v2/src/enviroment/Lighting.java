package enviroment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Screens.Quarto;

public class Lighting {

	Quarto quarto;
	BufferedImage darknessFilter;
	
	public Lighting(Quarto quarto) {
		
		this.quarto = quarto;
		
		setLightSource();
	} 
	
	public void setLightSource() {
		
		// CRIANDO UM BUFFERED IMAGE
		darknessFilter = new BufferedImage(quarto.larguraTela, quarto.alturaTela, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
			
		if(quarto.player.currentLight == null) {
			g2.setColor(new Color(0, 0, 0, 0.98f));
		}
		else {
			// PEGANDO O MEIO DE X E Y PARA O CIRCULO DE LUZ
			int centerX = quarto.player.screenX + (quarto.tileSize) / 2;
			int centerY = quarto.player.screenY + (quarto.tileSize) / 2;
				
			// CRIANDO UMA GRADAÇÃO NO CIRCULO DA LUZ
			Color color[] = new Color[12];
			float fraction[] = new float[12];
			
			color[0] = new Color(0, 0, 0, 0.1f);
			color[1] = new Color(0, 0, 0, 0.42f);
			color[2] = new Color(0, 0, 0, 0.52f);
			color[3] = new Color(0, 0, 0, 0.61f);
			color[4] = new Color(0, 0, 0, 0.69f);
			color[5] = new Color(0, 0, 0, 0.76f);
			color[6] = new Color(0, 0, 0, 0.82f);
			color[7] = new Color(0, 0, 0, 0.87f);
			color[8] = new Color(0, 0, 0, 0.91f);
			color[9] = new Color(0, 0, 0, 0.94f);
			color[10] = new Color(0, 0, 0, 0.96f);
			color[11] = new Color(0, 0, 0, 0.98f);
			
			
			fraction[0] = 0f;
			fraction[1] = 0.4f;
			fraction[2] = 0.5f;
			fraction[3] = 0.6f;
			fraction[4] = 0.65f;
			fraction[5] = 0.7f;
			fraction[6] = 0.75f;
			fraction[7] = 0.8f;
			fraction[8] = 0.85f;
			fraction[9] = 0.9f;
			fraction[10] = 0.95f;
			fraction[11] = 1f;
			
			// CRIANDO UMA CONFIGURAÇÃO PARA PINTAR A GRADAÇÃO DO CIRCULO DE LUZ
			RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, quarto.player.currentLight.lightRadius, fraction, color);
			
			// SETTANDO OS DADOS DA GRADAÇÃO NO G2
			g2.setPaint(gPaint);
		}
		g2.fillRect(0, 0, quarto.larguraTela, quarto.alturaTela);
		
		g2.dispose();
	}
	
	public void update() {
		
		if(quarto.player.lightUpdated == true) {
			setLightSource();
			quarto.player.lightUpdated = false;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		g2.drawImage(darknessFilter, 0, 0, null);
	}
}
