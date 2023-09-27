package controller.playState.entityController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.enemyController.EnemyController;

public class BulletController {

	private boolean hit = false;
	private int indexInList;
	private int direction;
	private Hitbox hitbox;
	private float speed;
	private PlayStateController play;
	//il proiettile non colpisce chi lo ha lanciato, target è la entità che viene colpita
	private EntityController owner, target;  
	private int distanzaPercorsa;
	
	public BulletController(PlayStateController p, int index, EntityController e) {
		play = p;
		setHitbox(e);
		speed = play.getController().getGameScale()*1.3f;
		indexInList = index;
		
		direction = e.currentDirection;
		owner = e;
	}

	private void setHitbox(EntityController e) {
		
		int width = e.getHitbox().width;
		int height = e.getHitbox().height;
		float x = e.getHitbox().x;
		float y = e.getHitbox().y;
		
		hitbox = new Hitbox((int)x, (int)y, width, height);
	}

	public void update() {
		checkCollision();
		updatePosition();
	}

	private void checkCollision() {
		try {
			switch(direction) {
			case EntityController.LEFT:
				//prima controlla se si schianta contro una parete
				if(!play.getCollisionChecker().canMoveLeft(hitbox)) {
					hit = true;
					target = null;
				}
				else {
					//se non si schianta contro una parete, vede se si schianta contro una entità
					target = play.getCollisionChecker().bulletHittedEntity(hitbox, owner);
					if(target != null) {
						//in caso positivo, si salva il riferimento a quella entità
						hit = true;
					}
				}	  	
				break;
				
			case EntityController.UP: 
				if(!play.getCollisionChecker().canMoveUp(hitbox)) {
					hit = true;
					target = null;
				}
				else {
					target = play.getCollisionChecker().bulletHittedEntity(hitbox, owner);
					if(target != null) {
						hit = true;
					}
				}	  	
				break;
				
			case EntityController.DOWN: 
				if(!play.getCollisionChecker().canMoveDown(hitbox)) {
					hit = true;
					target = null;
				}
				else {
					target = play.getCollisionChecker().bulletHittedEntity(hitbox, owner);
					if(target != null) {
						hit = true;
					}
				}	  	
				break;
				
			case EntityController.RIGHT: 
				if(!play.getCollisionChecker().canMoveRight(hitbox)) {
					hit = true;
					target = null;
				}
				else {
					target = play.getCollisionChecker().bulletHittedEntity(hitbox, owner);
					if(target != null) {
						hit = true;
					}
				}	  	
				break;
			}		
		}
		catch(ArrayIndexOutOfBoundsException obe) {
			play.getController().getView().getPlay().removeBullet(indexInList);
			play.removeBullets(indexInList);
			System.out.println("fuori dai bordi");
		}
							
		if(hit) {
			//se si è schiantato contro una entità, se non è un npc, abbassa la sua vita
			if(target != null) {
				if(target.typeOfTarget.compareTo("enemy") == 0 && owner.typeOfTarget.compareTo("player") == 0) {
					EnemyController enemy = (EnemyController)target;
					enemy.hitted(10, direction,false);
				}
				
				else if(target.typeOfTarget.compareTo("player") == 0) {
					play.getPlayer().hitted(10, direction);
				}
			}
			
			play.getController().getView().getPlay().removeBullet(indexInList);
			play.removeBullets(indexInList);
		}		
	}

	private void updatePosition() {
		switch(direction) {
		case EntityController.LEFT:
			hitbox.x -= speed;
			break;
		case EntityController.RIGHT:
			hitbox.x += speed;
			break;
		case EntityController.UP:
			hitbox.y -= speed;
			break;
		case EntityController.DOWN:
			hitbox.y += speed;
			break;
		}
		//il proiettile dopo qualche metro si ferma
		distanzaPercorsa += speed; 
		if(distanzaPercorsa >= 3*play.getController().getTileSize()) {
			play.getController().getView().getPlay().removeBullet(indexInList);
			play.removeBullets(indexInList);
		}
			
	}

	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public int getDirection() {
		return direction;
	}

	public void decreaseIndexInList() {
		indexInList--;	
	}
}