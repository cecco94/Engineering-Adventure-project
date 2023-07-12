package view.mainMenu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.Gamestate;
import view.ViewUtils;

public class GeneralButton extends MenuButton {
	
	public GeneralButton(String[] percorsoIcone, int y, int width, int height, Gamestate state) {
		loadIcons(percorsoIcone, width, height);
		super.setBounds(ViewUtils.getCenteredXPos(width), y, width, height);
		newState = state;
	}

	private void loadIcons(String[] percorsoIcone, int width, int height) {
		BufferedImage temp;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream(percorsoIcone[0]));
			mouseAwayImage = ViewUtils.scaleImage(temp, width, height);
			temp = ImageIO.read(getClass().getResourceAsStream(percorsoIcone[1]));
			mouseOverImage = ViewUtils.scaleImage(temp, width, height);
			temp = ImageIO.read(getClass().getResourceAsStream(percorsoIcone[2]));
			mousePressedImage = ViewUtils.scaleImage(temp, width, height);	
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(mouseAwayImage, (int)bounds.getX(), (int)bounds.getY(), null);
		if(mouseOver)
			g2.drawImage(mouseOverImage, (int)bounds.getX(), (int)bounds.getY(), null);
		if(mousePressed)
			g2.drawImage(mousePressedImage, (int)bounds.getX(), (int)bounds.getY(), null);		
	}

}
