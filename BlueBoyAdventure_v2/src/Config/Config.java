package Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Screens.Quarto;

public class Config {
	
	Quarto quarto;
	
	public Config(Quarto quarto) {
		
		this.quarto = quarto;
	}
	
	public void saveConfig() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			// TELA CHEIA
			if(quarto.fullScreenOn == true) {
				bw.write("On");
			}
			if(quarto.fullScreenOn == false) {
				bw.write("Off");
			}
			bw.newLine();
			
			// VOLUME DA MUSICA
			bw.write(String.valueOf(quarto.music.volumeScale));
			bw.newLine();
			
			// VOLUME DO SOM
			bw.write(String.valueOf(quarto.se.volumeScale));
			bw.newLine();
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			String s = br.readLine();
			
			// TELA CHEIA
			if(s.equals("On")) {
				quarto.fullScreenOn = true;
			}
			if(s.equals("Off")) {
				quarto.fullScreenOn = false;
			}
			
			// VOLUME MUSICA
			s = br.readLine();
			quarto.music.volumeScale = Integer.parseInt(s);
			
			// VOLUME DO SOM
			s = br.readLine();
			quarto.se.volumeScale = Integer.parseInt(s);
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
