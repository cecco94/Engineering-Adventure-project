package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public abstract class EntityController {

	protected Rectangle hitbox;
	protected int speed;
	protected PlayStateController play;
	protected boolean moving, idle, attacking, up, down, left, right;
	public static int DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	public static int IDLE = 0, MOVE = 1, ATTACK = 2, THROW = 3;
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
	}
	
	public abstract void update();

	public String toString() {
		return "( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
	public int getType() {
		return type;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public int getCurrentAction() {
		if(idle) {
			System.out.println("idle");
			return IDLE;
		}
		
		else if(moving) {
			System.out.println("move");
			return MOVE;
		}
	 
		else if(attacking) {
			System.out.println("attack");
			return ATTACK;
		}
		
		else {
			System.out.println("throw");
			return THROW;	
		}
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
}
