package enviroment;

import java.awt.Graphics2D;

import Screens.Quarto;

public class EnvironmentManager {

	Quarto quarto;
	public Lighting lighting;
	
	public EnvironmentManager(Quarto quarto) {
		
		this.quarto = quarto;
	}
	
	public void setup() {
		
		lighting = new Lighting(quarto);
	}
	
	public void update() {
		
		lighting.update();
	}
	
	public void draw(Graphics2D g2) {
		
		lighting.draw(g2);
	}
}
