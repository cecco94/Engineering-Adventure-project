package view.playState.entityView.npcview;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class PupaView extends NPCView {

	public PupaView(IView v, int index) {
		
		super(v, index);
		
		
		this.type = "npc";
		
		
		loadImages();	
		setDialogues();
		
		xOffset = (int)(0*GamePanel.SCALE); //3;
		yOffset = (int)(0*GamePanel.SCALE); //3;
		animationSpeed = 40;	
	}
	
	private void loadImages() {
	
	BufferedImage image = null;
	BufferedImage temp = null;
	
	animation = new BufferedImage[1][2][][];		//un tipo di vecchio, due azioni

	animation[0][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immagini
	animation[0][MOVE] = new BufferedImage[4][4];		//ci sono 4 direzioni, ogni direzione ha 4 immagini
	
	loadRunImages(image, temp);
	loadIdleImages();
	
	}
	
	private void loadIdleImages() {
		//prendi le immagini già caricate, prendi la seconda ogni tre		
			for(int direction = 0;  direction < 4; direction++)
					animation[0][IDLE][direction][0] = animation[0][MOVE][direction][1]; 
		
	}
	
	private void loadRunImages(BufferedImage image, BufferedImage temp) {

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/pupa.png"));
			
			
			temp = image.getSubimage(0, 0, 16, 31);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][DOWN][0] = temp;
			
			temp = image.getSubimage(16, 0, 17, 30);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][DOWN][1] = temp;
			
			temp = image.getSubimage(16 + 17, 0, 17, 31);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][DOWN][2] = temp;
			
			temp = image.getSubimage(16 + 17 + 17, 0, 16, 30);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][DOWN][3] = temp;
			

			

			temp = image.getSubimage(0, 31, 23, 31);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][RIGHT][0] = temp;
			
			temp = image.getSubimage(23, 31, 23, 30);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][RIGHT][1] = temp;
			
			temp = image.getSubimage(23 + 23, 31, 23, 31);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][RIGHT][2] = temp;
			
			temp = image.getSubimage(23 + 23 + 23, 31, 23, 30);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][RIGHT][3] = temp;
			
			
			
			
			temp = image.getSubimage(0, 62, 23, 31);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][LEFT][0] = temp;
			
			temp = image.getSubimage(23, 62, 23, 30);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][LEFT][1] = temp;
			
			temp = image.getSubimage(23 + 23, 62, 23, 31);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][LEFT][2] = temp;
			
			temp = image.getSubimage(23 + 23 + 23, 62, 23, 30);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
			animation[0][MOVE][LEFT][3] = temp;
			
			

			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*17, 93, 17, 30);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
				animation[0][MOVE][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void setDialogues() {
		dialogues = new String[6];
		dialogues[0] = "Fortuna che ci sei !";
		dialogues[1] = "Nel dormitorio è saltata la luce, non vedo più niente !";
		dialogues[2] = "Mi fa paura il buio \n non posso vedere il mio bellissimo faccino riflesso";
		dialogues[3] = "Se riesci ad accendere la luce \n ti consiglio un esame a scelta molto facile \n con tanti CFU";
		dialogues[4] = "Vai a destra, nella parte maschile del dormitorio,\n lì troverai il modo per accendere la luce";
		dialogues[5] = "So che puoi farcela !";
		
	}

	protected int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		
		else if(currentAction == MOVE)
			return 4;

		return 0;
	}

}
