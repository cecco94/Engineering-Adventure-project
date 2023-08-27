package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public abstract class EntityController {

	//il punto in alto a sinistra della hitbox è la posizione dell'entità nella mappa
	protected Rectangle hitbox, tempHitboxForCheck;
	protected int speed;
	protected PlayStateController play;
	protected boolean moving, idle, attacking, up, down, left, right;
	protected int direction;
	public static final int DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	public static final int IDLE = 0, MOVE = 1, ATTACK = 2, THROW = 3;
	protected int currentAction = IDLE;
	protected int type; //per capire, se è un NPC quale sia
	
	public EntityController (Rectangle r, PlayStateController p) {
		play = p;
		hitbox = r;
		
		//settiamo la posizione nella mappa e scaliamo la dimensione
		hitbox.x *= GamePanel.TILES_SIZE; 
		hitbox.y *= GamePanel.TILES_SIZE; 
		
		hitbox.width *= GamePanel.SCALE;
		hitbox.height *= GamePanel.SCALE;
		
		//hitbox che serve per le collisioni, prima di cambiare la hitbox, cambiamo questa
		//se controllando le collisioni va tutto bene, cambiamo anche la hitbox
		tempHitboxForCheck = new Rectangle(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		
		resetBooleans();
		direction = DOWN;

	}
	
	protected void setBounds(int x, int y, int w, int h) {
		hitbox.x = x;
		tempHitboxForCheck.x = x;
		
		hitbox.y = y;
		tempHitboxForCheck.y = y;
		
		hitbox.width = w;
		tempHitboxForCheck.width = w;
		
		hitbox.height = h;
		tempHitboxForCheck.height = h;
		
	}
	
	public abstract void update();
	
	public int getType() {
		return type;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public int getCurrentAction() {
		if(idle) 
			return IDLE;
	
		else if(moving) 
			return MOVE;
	 
		else if(attacking) 
			return ATTACK;
		
		else 
			return THROW;	
		
	}
	
	public int getCurrentDirection() {
		if(up)
			return UP;
		
		else if(down)
			return DOWN;
		
		else if(left)
			return LEFT;
		else 
			return RIGHT;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public boolean isRight() {
		return right;
	}
	
	public boolean isLeft() {
		return left;
	}
	
	public boolean isDown() {
		return down;
	}
	
	public boolean isUp() {
		return up;
	}

	public void setMoving(boolean m) {
		moving = m;
	}
	
	protected void resetBooleans() {
		up = false;
		down = false;
		left = false;
		right = false;	
		idle = false;
		moving = false;	
	}
	
	protected void resetDirection() {
		up = false;
		down = false;
		left = false;
		right = false;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public String toString() {
		return "( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
}
