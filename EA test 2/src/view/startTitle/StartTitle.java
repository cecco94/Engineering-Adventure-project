package view.startTitle;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Gamestate;
import view.GamePanel;


public class StartTitle {
	
	private BufferedImage[] titoli;
	private int timer = 120*10;
	private int timerPrimaScritta = 120*5;
	private int counter = 0;
	private int scritta1X, scritta1Y, scritta2X, scritta2Y;
	private float alPhaValue;
	
	public StartTitle() {
		titoli = new BufferedImage[2];
		getImages();
		scritta1X = GamePanel.GAME_WIDTH/2 - titoli[0].getWidth()/2;
		scritta1Y = GamePanel.GAME_HEIGHT/2 - titoli[0].getHeight()/2;
		scritta2X = GamePanel.GAME_WIDTH/2 - titoli[1].getWidth()/2;
		scritta2Y = GamePanel.GAME_HEIGHT/2 - titoli[1].getHeight()/2;
		
	}

	private void getImages() {
		try {
			titoli[0] = ImageIO.read(getClass().getResourceAsStream("/startTitle/gngames.png"));
			titoli[1] = ImageIO.read(getClass().getResourceAsStream("/startTitle/presenta.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void drawYourself(Graphics2D g2) {
		counter++;

		if(counter > timer) {
			skipTitle();
			return;
		}
		
		if(counter < timerPrimaScritta) {
			alPhaValue = (float) counter/(timerPrimaScritta);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));
			g2.drawImage(titoli[0], scritta1X, scritta1Y, null);
		}
		
		else if(counter >= timerPrimaScritta){
			alPhaValue = (float) (counter - timerPrimaScritta)/timerPrimaScritta;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));
			g2.drawImage(titoli[1], scritta2X, scritta2Y, null);
		}	
	}
	
	public void skipTitle() {
		Gamestate.state = Gamestate.MAIN_MENU;
	}
	
	

}