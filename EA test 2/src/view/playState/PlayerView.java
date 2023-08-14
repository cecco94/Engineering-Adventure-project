package view.playState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import view.ViewUtils;
import view.main.GamePanel;
import view.main.IView;

//classe che contiene la parte grafica del giocatore
public class PlayerView extends SortableElement{

	//per l'animazione
	private int animationCounter = 0;
	private int animationSpeed = 20;
	private int numSprite = 0;
	private BufferedImage[][][] playerAnimation;	// primo campo = azione, secondo = direzione, terzo = immagine
	private final int IDLE = 0, RUN = 1, DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	private int currentAction = IDLE;
	private int currentDirection = DOWN;
	private static final int RAGAZZO = 0, RAGAZZA = 1;
	private int avatarType = RAGAZZA;
	
	//per capire la posizione del player usiamo la hitbox, per capire se sta davanti o dietro qualcosa ci servono due offset
	//perchè la hitbox non coincide con la dimensione completa del personaggio, essendo la prima più piccola
	public static int xOffset, yOffset;		
	
	//la posizione del player è sempre al centro della finestra di gioco
	public static final int xOnScreen = GamePanel.GAME_WIDTH/2 - GamePanel.TILES_SIZE/2;
	public static final int yOnScreen = GamePanel.GAME_HEIGHT/2 - GamePanel.TILES_SIZE/2;
	
	private IView view;
	
	public PlayerView(IView v) {
		
		this.typeElemtToSort = 4;		//elemento animato, da disegnare sopra la mappa
		view = v;
		
		loadImages();	
		
		//difference between xitbox and image of player
		xOffset = (int)(GamePanel.TILES_SIZE*0.10);		// = 4, perchè la hitboxX è il 75% di un tile
		yOffset = GamePanel.TILES_SIZE/2;  				// = 24, perchè la hitboxY è metà di un tile
		
		//servono per poter comparare il player con gli altri elementi grafici da ordinare
		xPosMapForSort = view.getController().getPlay().getPlayer().getHitbox().x - xOffset;
		yPosMapForSort = view.getController().getPlay().getPlayer().getHitbox().y - yOffset;

	}
	
	//mettiamo tutte le animazioni un una tabella. prima selezioniamo ogni sottoimmagine poi le scaliamo.
	//ogni azione ha un array di immagini che mettiamo in loop una dopo l'altra facendo aumentare un indice
	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		playerAnimation = new BufferedImage[2][][];
		loadIdleImages(image, temp);
		loadRunImages(image, temp);	
	}

	private void loadIdleImages(BufferedImage image, BufferedImage temp) {
		playerAnimation[IDLE] = new BufferedImage[4][4];		//ci sono 4 direzioni, ogni direzione ha 4 immagini
		if(avatarType == RAGAZZO) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/player/idleSpriteBoy.png"));
				
				for(int i = 0; i < 4; i++) {
					temp = image.getSubimage(i*24, 0, 24, 33);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[IDLE][DOWN][i] = temp;
				}
				
				for(int i = 0; i < 4; i++) {
					temp = image.getSubimage(i*21, 33, 21, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[IDLE][RIGHT][i] = temp;
				}
				
				for(int i = 0; i < 4; i++) {
					temp = image.getSubimage(i*21, 33 + 32, 21, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[IDLE][LEFT][i] = temp;
				}
				
				for(int i = 0; i < 4; i++) {
					temp = image.getSubimage(i*22, 33 + 32 + 32, 22, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[IDLE][UP][i] = temp;
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if(avatarType == RAGAZZA) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/player/idleGirl.png"));
				
				for(int i = 0; i < 4; i++) {
					temp = image.getSubimage(i*23, 0, 23, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[IDLE][DOWN][i] = temp;
				}
				
				for(int i = 0; i < 4; i++) {
					temp = image.getSubimage(i*22, 32, 22, 31);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[IDLE][RIGHT][i] = temp;
				}
				
				for(int i = 0; i < 4; i++) {
					temp = image.getSubimage(i*22, 32 + 31, 22, 31);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[IDLE][LEFT][i] = temp;
				}
				
				for(int i = 0; i < 4; i++) {
					temp = image.getSubimage(i*22, 32 + 31 + 31, 22, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[IDLE][UP][i] = temp;
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		playerAnimation[RUN] = new BufferedImage[4][6];		//ci sono 4 direzioni, ogni direzione ha 6 immagini
		if(avatarType == RAGAZZO) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/player/walkingSpritesBoyCorr.png"));
				
				for(int i = 0; i < 6; i++) {
					temp = image.getSubimage(i*23, 0, 23, 35);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[RUN][DOWN][i] = temp;
				}
				
				for(int i = 0; i < 6; i++) {
					temp = image.getSubimage(i*24, 35, 24, 33);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[RUN][RIGHT][i] = temp;
				}
				
				for(int i = 0; i < 6; i++) {
					temp = image.getSubimage(i*24, 35 + 33, 24, 33);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[RUN][LEFT][i] = temp;
				}
				
				for(int i = 0; i < 6; i++) {
					temp = image.getSubimage(i*26, 35 + 33 + 33, 26, 36);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[RUN][UP][i] = temp;
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if(avatarType == RAGAZZA) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/player/MoveGirl.png"));
				
				for(int i = 0; i < 6; i++) {
					temp = image.getSubimage(i*26, 0, 26, 34);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[RUN][DOWN][i] = temp;
				}
				
				for(int i = 0; i < 6; i++) {
					temp = image.getSubimage(i*24, 34, 24, 33);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[RUN][RIGHT][i] = temp;
				}
				
				for(int i = 0; i < 6; i++) {
					temp = image.getSubimage(i*24, 34 + 33, 24, 33);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[RUN][LEFT][i] = temp;
				}
				
				for(int i = 0; i < 6; i++) {
					temp = image.getSubimage(i*31, 34 + 33 + 33, 31, 34);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
					playerAnimation[RUN][UP][i] = temp;
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void setMapPositionForSort(int posizPlayerX, int posizPlayerY) {
		xPosMapForSort = posizPlayerX - xOffset;
		yPosMapForSort = posizPlayerY - yOffset;
	}

	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	@Override
	public void draw(Graphics2D g2, int x, int y) {	
		animationCounter++;
		if (animationCounter > animationSpeed) {
			numSprite++;	
			setDirection();
			
			if(numSprite >= getAnimationLenght())
				numSprite = 0;	
			
			animationCounter = 0;
		}
		
		g2.drawImage(playerAnimation[currentAction][currentDirection][numSprite], xOnScreen, yOnScreen, null);	
	//	g2.drawRect(xOnScreen + xOffset, yOnScreen + yOffset, view.getController().getPlay().getPlayer().getHitbox().width, view.getController().getPlay().getPlayer().getHitbox().height);
	}

	private int getAnimationLenght() {
		if(currentAction == IDLE)
			return 4;
		else if(currentAction == RUN)
			return 6;
		
		return 0;
	}
	
	public void setDirection() {
		if(view.getController().getPlay().getPlayer().isMoving())
			currentAction = RUN;
		else
			currentAction = IDLE;
		
		if(view.getController().getPlay().getPlayer().isLeft())
			currentDirection = LEFT;
		
		else if(view.getController().getPlay().getPlayer().isRight())
			currentDirection = RIGHT;
		
		else if(view.getController().getPlay().getPlayer().isDown())
			currentDirection = DOWN;
		
		else if(view.getController().getPlay().getPlayer().isUp())
			currentDirection = UP;
		
	}
	
	//idle ragazzo 24x33 davanti, 21x32 lato, 22x32 dietro	//idle ragazza 23x32 davanti, 22x31 lato, 22X32 dietro
	//walk ragazzo 23x35 davanti, 24x33 lato, 26x36 dietro 	//walk ragazza 26x34 davanti, 24x33 lato, 31x34 dietro
}













