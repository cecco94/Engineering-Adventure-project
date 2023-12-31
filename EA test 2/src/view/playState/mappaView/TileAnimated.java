package view.playState.mappaView;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import view.ViewUtils;
import view.main.GamePanel;

//ha due immagini che cambiano creando una animazione, estende il tile in modo da poter fare una lista unica di tile
public class TileAnimated extends Tile {

	private BufferedImage secondImage;
	private int counter, freq, currentImage = 1;
	
	public TileAnimated(BufferedImage img1, BufferedImage img2) {
		super(img1);
		image = ViewUtils.scaleImage(img1, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
		secondImage = img2;	
		secondImage = ViewUtils.scaleImage(img2, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
		
		int min = 120;	//un secondo, perchè il gioco ha 120 fps
		int max = 360;	//tre secondi
		freq = ThreadLocalRandom.current().nextInt(min, max + 1);	//numero casuale
	}

	public TileAnimated(BufferedImage img1, BufferedImage img2, int freq) {
		super(img1);
		image = ViewUtils.scaleImage(img1, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
		secondImage = img2;	
		secondImage = ViewUtils.scaleImage(img2, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
	
		this.freq = freq;	//numero dato
	}
	
	public BufferedImage getImage() {
		selectImageToShow();
		
		if(currentImage == 1)
			return image;
		
		return secondImage;		
	}

	private void selectImageToShow() {
		counter++;
		if (counter >= freq) {
			
			if(currentImage == 1) 
				currentImage = 2;
			else 
				currentImage = 1;	
			
			counter = 0;
		}		
	}
}
