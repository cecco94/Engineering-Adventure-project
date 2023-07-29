package view.gameBegin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JProgressBar;

// schermata di caricamento del gioco
public class SplashScreenGame extends JFrame implements Runnable {
	   
	private int frameWidth = 600;
	private int frameHeight = 400;
	private JProgressBar progressBar;
	private static final long serialVersionUID = 1L;
	
	public SplashScreenGame() {
	//   System.out.println("thread di creazione splashschreen " + Thread.currentThread().getId());
		createProgressBar();
		
		Thread loadingThread = new Thread(this);
		loadingThread.start();
	}

	private void createProgressBar() {
		   progressBar = new JProgressBar();
		   progressBar.setBounds(frameWidth/2 - 400/2, frameHeight/2 + 50, 400,20);
	       progressBar.setBorderPainted(true);
	       progressBar.setStringPainted(true);
	       progressBar.setBackground(Color.black);
	       progressBar.setForeground(Color.red);
	       progressBar.setValue(0);
		
	}

	@Override
	public void run() {
		createAndShowGUI();
	//	System.out.println("thread dove si mostra splash screen " + Thread.currentThread().getId());
	}

	private void createAndShowGUI() {
	   
	   JLabel sfondo = new JLabel();
	   sfondo.setOpaque(true);
	   ImageIcon imageBackground = new ImageIcon(getClass().getResource("/loading/tech.gif"));
	   imageBackground.setImage(imageBackground.getImage().getScaledInstance(frameWidth, frameHeight, Image.SCALE_DEFAULT));
	   sfondo.setIcon(imageBackground);	
	   sfondo.setBounds(0,0,frameWidth,frameHeight);
		
	   JLabel scritta = new JLabel();
	   scritta.setOpaque(true);
	   scritta.setBackground(new Color(0,0,0,0));
	   ImageIcon immagineScritta = new ImageIcon(getClass().getResource("/loading/caricamento.png"));
	   immagineScritta.setImage(immagineScritta.getImage().getScaledInstance(300, 30, Image.SCALE_DEFAULT));
	   scritta.setIcon(immagineScritta);
	   scritta.setBounds(frameWidth/2 - immagineScritta.getIconWidth()/2, frameHeight/2 , 
			   				immagineScritta.getIconWidth(), immagineScritta.getIconHeight());	   
	   
	   JLayeredPane layeredPane = new JLayeredPane();
	   layeredPane.setBounds(0,0, frameWidth, frameHeight);
	   layeredPane.add(sfondo, Integer.valueOf(1));
	   layeredPane.add(scritta, Integer.valueOf(2));
	   layeredPane.add(progressBar, Integer.valueOf(3));
	    
      add(layeredPane);
      setUndecorated(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(new Dimension(frameWidth, frameHeight));
      setLayout(null);
      setLocationRelativeTo(null);
      requestFocus();
      setVisible(true);
      	
	}
	
	public void showProgress(int i) {
	//	System.out.println("thread di modifica barra " + Thread.currentThread().getId());
	    progressBar.setValue(i);
	}
}