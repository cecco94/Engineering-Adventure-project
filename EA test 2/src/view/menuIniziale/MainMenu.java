package view.menuIniziale;

import static view.main.GamePanel.GAME_HEIGHT;
import static view.main.GamePanel.GAME_WIDTH;
import static view.main.GamePanel.SCALE;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.Gamestate;
import view.ViewUtils;
import view.main.IView;

public class MainMenu extends Menu{
	private IView view;
	
	private int indexBackground, counterbackground, counterTitle;
	private int timer = 120*10;
	
	private String credits = "©GNgame Production";
	private BufferedImage[] sfondoAnimato;
	private BufferedImage titolo;
	private int titoloX, titoloY;
	private int altezzaBottoni, distanzaBottoni;
	
	private final int PLAY = 0, LOAD = 1, OPTION = 2, EXIT = 3;
	private int buttonIndex = PLAY;
	
	public MainMenu(IView v) {
		view = v;
		setDimensions();
		loadBackgroundImages();
		loadTitle();
		loadButtons();
	}
	
	private void setDimensions() {
		altezzaBottoni = (int)(250 * SCALE);
		distanzaBottoni = (int)(30 * SCALE);
	}
	
	private void loadBackgroundImages() {
		sfondoAnimato = new BufferedImage[24];
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-0.png"));
			sfondoAnimato[0] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-1.png"));
			sfondoAnimato[1] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-2.png"));
			sfondoAnimato[2] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-3.png"));
			sfondoAnimato[3] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-4.png"));
			sfondoAnimato[4] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-5.png"));
			sfondoAnimato[5] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-6.png"));
			sfondoAnimato[6] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-7.png"));
			sfondoAnimato[7] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-8.png"));
			sfondoAnimato[8] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-9.png"));
			sfondoAnimato[9] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-10.png"));
			sfondoAnimato[10] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-11.png"));
			sfondoAnimato[11] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-12.png"));
			sfondoAnimato[12] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-13.png"));
			sfondoAnimato[13] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-14.png"));
			sfondoAnimato[14] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-15.png"));
			sfondoAnimato[15] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-16.png"));
			sfondoAnimato[16] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-17.png"));
			sfondoAnimato[17] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-18.png"));
			sfondoAnimato[18] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-19.png"));
			sfondoAnimato[19] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-20.png"));
			sfondoAnimato[20] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-21.png"));
			sfondoAnimato[21] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-22.png"));
			sfondoAnimato[22] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-23.png"));
			sfondoAnimato[23] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadTitle() {
		try {
			titolo = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/scritta_titolo.png"));	
			titolo = ViewUtils.scaleImage(titolo, titolo.getWidth() * SCALE, titolo.getHeight() * SCALE);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		titoloX = ViewUtils.getCenteredXPos(titolo.getWidth());
		titoloY = GAME_HEIGHT/4;
	}

	private void loadButtons() {
		String[] percorsoIscriviti = {"/menuiniziale/iscriviti1.png", "/menuiniziale/iscriviti2.png", "/menuiniziale/iscriviti3.png"};
		String[] percorsoRiprendi = {"/menuiniziale/riprendi1.png", "/menuiniziale/riprendi2.png", "/menuiniziale/riprendi3.png"};
		String[] percorsoOpzioni = {"/menuiniziale/opzioni1.png", "/menuiniziale/opzioni2.png", "/menuiniziale/opzioni3.png"};	
		String[] percorsoQuit = {"/menuiniziale/rinuncia1.png", "/menuiniziale/rinuncia2.png", "/menuiniziale/rinuncia3.png"};
		buttons = new MainMenuButton[4];
		buttons[0] = new MainMenuButton(percorsoIscriviti, altezzaBottoni,(int)(110*SCALE),(int)(16*SCALE), Gamestate.SELECT_AVATAR, view);
		buttons[1] = new MainMenuButton(percorsoRiprendi, altezzaBottoni + distanzaBottoni, (int)(300*SCALE), (int)(17*SCALE), Gamestate.LOAD_GAME, view);
		buttons[2] = new MainMenuButton(percorsoOpzioni, altezzaBottoni + distanzaBottoni*2, (int)(270*SCALE), (int)(16*SCALE), Gamestate.OPTIONS, view);
		buttons[3] = new MainMenuButton(percorsoQuit, altezzaBottoni + distanzaBottoni*3, (int)(250*SCALE), (int)(14*SCALE), Gamestate.QUIT, view);
	}	

	public void drawYourself(Graphics2D g2) {
		drawBackground(g2);
		drawCredits(g2);
		drawButtons(g2);
		drawTitle(g2);
	}

	public void drawBackground(Graphics2D g2) {
		counterbackground++;
		g2.drawImage(sfondoAnimato[indexBackground], 0, 0, null);
		if(counterbackground == 10) {
			indexBackground++;
			counterbackground = 0;
		}
		if(indexBackground == 23)
			indexBackground = 0;	
	}

	private void drawCredits(Graphics2D g2) {
		g2.setColor(Color.red);
		int x = ViewUtils.getXforCenterText(credits, g2);
		g2.drawString(credits, x, GAME_HEIGHT - (int)(2*SCALE));
	}
	
	private void drawTitle(Graphics2D g2) {
		counterTitle++;
		if(counterTitle < timer) {
			float alPhaValue = (float) counterTitle/(timer);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));	
		}
		g2.drawImage(titolo, titoloX, titoloY, null);
	}
	
	public void keyReleased(int tasto) {
		switch(buttonIndex) {
		case PLAY:
			comportamentoPlay(tasto);
			break;
		case LOAD:
			comportamentoLoad(tasto);
			break;
		case OPTION:
			comportamentoOpzioni(tasto);
			break;	
		case EXIT:
			comportamentoEsci(tasto);
			break;
		}
	}
	
	private void comportamentoPlay(int tasto) {
		if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP)
			view.setCursorPosition(GAME_WIDTH/2, (int)(altezzaBottoni + SCALE));
		
		else if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(GAME_WIDTH/2, (int)(altezzaBottoni + distanzaBottoni + SCALE));
			buttonIndex = LOAD;
		}
		else if(tasto == KeyEvent.VK_ENTER)
			view.changeGameState(buttons[PLAY].getButtonState());
	}
	
	private void comportamentoLoad(int tasto) {
		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(GAME_WIDTH/2, (int)(altezzaBottoni + distanzaBottoni*2 + SCALE));
			buttonIndex = OPTION;
		}
		else if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP) {
			view.setCursorPosition(GAME_WIDTH/2, (int)(altezzaBottoni + SCALE));
			buttonIndex = PLAY;
		}
		else if(tasto == KeyEvent.VK_ENTER) 
			view.changeGameState(buttons[LOAD].getButtonState());
	}

	private void comportamentoOpzioni(int tasto) {
		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(GAME_WIDTH/2, (int)(altezzaBottoni + distanzaBottoni*3 + SCALE));
			buttonIndex = EXIT;
		}
		else if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP) {
			view.setCursorPosition(GAME_WIDTH/2, (int)(altezzaBottoni + distanzaBottoni + SCALE));
			buttonIndex = LOAD;
			}
		else if(tasto == KeyEvent.VK_ENTER) 
			view.changeGameState(buttons[OPTION].getButtonState());
	}

	private void comportamentoEsci(int tasto) {
		if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP) {
			view.setCursorPosition(GAME_WIDTH/2, (int)(altezzaBottoni + distanzaBottoni*2 + SCALE));
			buttonIndex = OPTION;
			}
		else if(tasto == KeyEvent.VK_ENTER) 
			view.changeGameState(buttons[EXIT].getButtonState());		
	}

	
	
}