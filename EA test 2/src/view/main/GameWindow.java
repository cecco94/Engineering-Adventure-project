package view.main;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import view.IView;
import view.ViewUtils;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private IView view;

	public GameWindow(GamePanel gp, IView v) {
		view = v;
		setTitle("ENGINEERING ADVENTURE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setGameCursor(gp);
		getContentPane().add(gp);
		pack();
		setLocationRelativeTo(null);
		handleLostFocus();
	}

	//questo metodo serve per quando la finestra di gioco perde il focus
	//resetta i boolean della direzione del personaggio
	private void handleLostFocus() {
		addWindowFocusListener(new WindowFocusListener() { 
			@Override
			public void windowLostFocus(WindowEvent e) {
				view.getController().getPlay().getPlayer().resetBooleans(); 	
			}
			@Override
			public void windowGainedFocus(WindowEvent e) {
				
			}
		});
	}

	//per avere quella adorabile freccetta blu
	private void setGameCursor(GamePanel gp) {	
		BufferedImage mouseIcon = null;
		try {
			mouseIcon = ImageIO.read(getClass().getResourceAsStream("/cursor.png"));
			mouseIcon = ViewUtils.scaleImage(mouseIcon, GamePanel.SCALE*15, GamePanel.SCALE*20);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(mouseIcon,new Point(gp.getX(),gp.getY()),"custom cursor"));
	}
}
