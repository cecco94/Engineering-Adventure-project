package view.playState.entityView;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class RobotView extends EntityView {

	private BufferedImage[][][][] animation;
	private Rectangle lifeRect;
	private int maxLifeWidth;
	private boolean firstDeathSprite;
	
	public RobotView(IView v, int index) {
		super(v, index);
		this.type = "enemy";
		
		loadImages();
		
		xOffset = (int)(0*GamePanel.SCALE); 
		yOffset = (int)(2*GamePanel.SCALE); 
		animationSpeed = 30;
		maxLifeWidth = animation[0][0][0][0].getWidth();
		lifeRect = new Rectangle(0, 0, maxLifeWidth, (int)(3*GamePanel.SCALE));
		
	}

	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animation = new BufferedImage[1][6][][];		
		
		animation[0][MOVE] = new BufferedImage[4][3];	//ci sono 4 direzioni, ogni direzione ha 3 immagini
		animation[0][IDLE] = new BufferedImage[4][1];	//ci sono 4 direzioni, ogni direzione ha 1 immagine
		animation[0][DIE] = new BufferedImage[4][2];
		
		loadRunImages(image, temp);	
		loadIdleImages();
		loadDieImages(image, temp);
		
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/robotGiusto.png"));
						
			for(int direction = 0; direction < 4 ; direction++) {
				for(int img = 0; img < 3; img++) {
					temp = image.getSubimage(img*16, direction*24, 16, 24);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
					animation[0][MOVE][direction][img] = temp;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void loadIdleImages() {
		animation[0][IDLE][DOWN][0] = animation[0][MOVE][DOWN][0];
		animation[0][IDLE][RIGHT][0] = animation[0][MOVE][RIGHT][0];
		animation[0][IDLE][LEFT][0] = animation[0][MOVE][LEFT][0];
		animation[0][IDLE][UP][0] = animation[0][MOVE][UP][0];
		
	}

	private void loadDieImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/robotGiusto.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		temp = image.getSubimage(0*16, 4*24, 16, 24);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][DOWN][0] = temp;
		animation[0][DIE][RIGHT][0] = temp;
		
		temp = image.getSubimage(0*16, 5*24, 16, 24);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][LEFT][0] = temp;
		animation[0][DIE][UP][0] = temp;
		
		temp = image.getSubimage(1*16, 4*24, 24, 24);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][DOWN][1] = temp;
		animation[0][DIE][RIGHT][1] = temp;

		temp = image.getSubimage(1*16, 5*24, 24, 24);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][LEFT][1] = temp;
		animation[0][DIE][UP][1] = temp;

		
	}

	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		
		if(view.getController().isEnemyHitted(index))
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		animationCounter++;
		setAction(this);
		setDirection(this);
		
		if (animationCounter > animationSpeed) {
			numSprite++;
			//l'animazione si ferma con lui a terra
			setDyingAniIndex();
	
			if(numSprite >= getAnimationLenght())
				numSprite = 0;	
			
			animationCounter = 0;
		}
		
		//distanza nella mappa tra il punto in alto a sinistra della hitbox 
		//del player ed il punto in alto a sinistra della hitbox del gatto
		int distanceX = xPlayerMap - xPosMapForSort + xOffset;
		int distanceY = yPlayerMap - yPosMapForSort + yOffset;
		
		//ci serve un offset perchè la distanza del gatto nella mappa rispetto al player è riferita al punto in
		//alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
		xPosOnScreen = PlayerView.xOnScreen - distanceX - xOffset + PlayerView.getXOffset();
		yPosOnScreen = PlayerView.yOnScreen - distanceY - yOffset + PlayerView.getYOffset();
				
		try {
			g2.drawImage(animation[0][currentAction][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
			drawLife(g2);			
		}
		catch (ArrayIndexOutOfBoundsException a) {
			a.printStackTrace();
		}
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

	}

	private void drawLife(Graphics2D g2) {
		g2.setColor(Color.green);
		lifeRect.x = xPosOnScreen;
		lifeRect.y = yPosOnScreen - lifeRect.height;
		
		//la lunghezza del rettangolo deve essere proporzionale alla vita dell robot, la vita è già in percentuale
		int life = view.getController().getEnemyLife(index);
		lifeRect.width = life*maxLifeWidth/100;
		
		g2.fillRect(lifeRect.x, lifeRect.y, lifeRect.width, lifeRect.height);
		
	}

	private int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		
		else if(currentAction == MOVE)
			return 3;
		
		else if(currentAction == DIE)
			return 2;
		
		return 0;
	}

	private void setDyingAniIndex() {
		if(currentAction == DIE) {
			if(firstDeathSprite) {
				numSprite--;
				firstDeathSprite = false;
			}
			else 
				numSprite = getAnimationLenght() - 1;
		}
		
	}
}
