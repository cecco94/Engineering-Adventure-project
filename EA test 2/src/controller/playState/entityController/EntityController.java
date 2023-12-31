package controller.playState.entityController;

import java.util.ArrayList;
import java.util.Random;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.pathfinding.Node;

public abstract class EntityController {

	//il punto in alto a sinistra della hitbox è la posizione dell'entità nella mappa
	//tempHitboxForCheck serve per le collisioni, prima di cambiare la hitbox, cambiamo questa
	//se controllando le collisioni va tutto bene, cambiamo anche la hitbox
	protected Hitbox hitbox, tempHitboxForCheck;
	protected float speed;
	
	protected PlayStateController play;
	
	protected int currentDirection;
	public static final int DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	public static final int IDLE = 0, MOVE = 1, ATTACK = 2, PARRY = 3, THROW = 4, DIE = 5;
	protected int currentAction = IDLE;
	
	// per far camminare l'entità in modo randomico
	protected int actionCounter;
	protected Random randomGenerator = new Random();
	protected int randomAction, randomDirection;
	
	//per capire quale entità è
	protected String type;   
	//l'indice nella lista delle entità, deve essere uguale anche nel view
	protected int index;
	
	//possiamo usare type of target al posto del type?
	//questi int servono per capire se il proiettile si è schiantato su un npc, su un nemico o sul player
	protected int typeOfTarget;
	public static final int PLAYER = 0, NPC = 1, ENEMY = 2;
	
	//percorso che l'entità deve seguire
	protected ArrayList<Node> path;
	protected int currentPathIndex = 0;
	
	//per gestire gli npc ed i nemici come macchine a stati
	protected final int NORMAL_STATE = 0, IN_WAY = 1, GOAL_REACHED = 2; 
	protected int currentState = NORMAL_STATE;
	
	public EntityController (int ind, String type, Hitbox r, PlayStateController p) {
		index = ind;
		
		this.type = type;
		
		play = p;
		hitbox = r;
		
		//settiamo la posizione nella mappa e scaliamo la dimensione
		hitbox.x *= play.getController().getTileSize(); 
		hitbox.y *= play.getController().getTileSize(); 
		
		hitbox.width  *= play.getController().getGameScale();
		hitbox.height *= play.getController().getGameScale();
		
		tempHitboxForCheck = new Hitbox((int)hitbox.x, (int)hitbox.y, hitbox.width, hitbox.height);
		
		path = new ArrayList<>();
		
		currentDirection = DOWN;
		currentAction = IDLE;
		currentState = NORMAL_STATE;

	}
	
	//settiamo la hitbox per creare le entità acnhe se non conosciamo tutti i dati.
	protected void setBounds(int x, int y, int w, int h) {
		hitbox.x = (float)x;
		tempHitboxForCheck.x = (float)x;
		
		hitbox.y = (float)y;
		tempHitboxForCheck.y = (float)y;
		
		hitbox.width = w;
		tempHitboxForCheck.width = w;
		
		hitbox.height = h;
		tempHitboxForCheck.height = h;
		
	}
	
	public abstract void update(float playerX, float playerY);
	
	// molti npc si muovono a caso nella stanza usando questo medoto
	public void randomMove() {
		actionCounter++;	
		//ogni due secondi cambia azione e direzione 
		if(actionCounter >= 400) {
			choseDirection();
			choseAction();
		}
		
		if(currentAction == MOVE && canMove()) {
						
			if(currentDirection == UP) {
				hitbox.y -= speed;	
			}
			else if(currentDirection == DOWN) {
				hitbox.y += speed;
			}
			else if(currentDirection == LEFT) {
				hitbox.x -= speed;
			}
			else if(currentDirection == RIGHT) {
				hitbox.y += speed;
			}
			
		}
		else
			currentAction = IDLE;
		
	}
	
	private void choseAction() {
		randomAction = randomGenerator.nextInt(2);
		
		if (randomAction == IDLE) 
			currentAction = IDLE;
		
		else 
			currentAction = MOVE;
			
	}
	
	protected void choseDirection() {		
	//mettendo un counter anche qui, il gatto cambia direzione anche se sta fermo, muove il muso
		randomDirection = randomGenerator.nextInt(4);
		
		if(randomDirection == 0) { 
			currentDirection = DOWN;
		}
		
		else if (randomDirection == RIGHT) { 
			currentAction = RIGHT;
		}
		
		else if(randomDirection == LEFT) {
			currentDirection = LEFT;
		}
		
		else if(randomDirection == UP) {
			currentDirection = UP;
		}
		
		actionCounter = 0;
	}
	
	protected boolean canMove() {
		boolean canMove = false;
		
		if(currentDirection == UP) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) 
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					canMove = true;
		}
		
		if(currentDirection == DOWN) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) 
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					canMove = true;
		}
		
		if(currentDirection == LEFT) {
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) 
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					canMove = true;
		}
		
		if(currentDirection == RIGHT) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) 
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					canMove = true;
		
		}	
		
		return canMove;
		
	}

	//metodi usati per seguire il percorso trovato
	protected void searchThePath(int goalCol, int goalRow) {
		int startCol = (int)(hitbox.x)/play.getTileSize();
		int startRow = (int)(hitbox.y)/play.getTileSize();
		
		if(play.getPathFinder().existPath(startCol, startRow, goalCol, goalRow)) {
			currentState = IN_WAY;
			play.getPathFinder().getPathToEntity(path);
		}
		
	}
	
	protected void goTrhoughtSelectedPath() {
		
		boolean canFollowThePath = true;
		//se è arrivato ad un tile del percorso, va al successivo
		if(hitbox.x == path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize() &&
		   hitbox.y == path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize()) {
				currentPathIndex++;
		}
		else {
			//per andare al successivo, vede quale direzione prendere
			float yDistance = hitbox.y - path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();
			float xDistance = hitbox.x - path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();
			int directionToCheck = 999;
			//prima controlla se deve salire o scendere
			if(yDistance < 0)
				directionToCheck = DOWN;
			
			else if(yDistance > 0)
				directionToCheck = UP;
			
			//se non deve salire o scendere, vede se deve andare a destra o a sinistra
			else if(yDistance == 0) {
								
				if(xDistance > 0)
					directionToCheck = LEFT;
				
				else if(xDistance < 0)
					directionToCheck = RIGHT;
			}
			
			//capita la direzione da prendere, entra in questo switch
			switch (directionToCheck) {
			case DOWN:
				canFollowThePath = goDown(yDistance);
				break;

			case UP:
				canFollowThePath = goUp(yDistance);
				break;
			
			case RIGHT:
				canFollowThePath = goRight(xDistance);
				break;
				
			case LEFT:
				canFollowThePath = goLeft(xDistance);
				break;
			}
		}
		//se per qualche ragione il percorso che deve seguire è bloccato, smette di andarci
		if(canFollowThePath == false) {
			currentAction = IDLE;
			currentState = GOAL_REACHED;
			path.clear();
			currentPathIndex = 0;
		}
		
	}

	protected boolean goDown(float yDistance) {
		//se la distanza è maggiore di un passo, fai un passo (se non ti schianti contro qualcun altro)
		currentDirection = DOWN;
		boolean canGo = true;
		
		if(Math.abs(yDistance) > speed) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
				hitbox.y = tempHitboxForCheck.y;
				
			//se ti schianti contro qualcuno, o qualcosa, rivedi il percorso che devi fare
			else 
				canGo = false;			
		}
		//se invece la distanza è <= un passo, spostati direttamente lì
		else if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
			hitbox.y = path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();	
		
		return canGo;
	}

	protected boolean goUp(float yDistance) {
		currentDirection = UP;
		boolean canGo = true;

		if (Math.abs(yDistance) > speed) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
				hitbox.y = tempHitboxForCheck.y;
			
			else 
				canGo = false;
			
				
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
			hitbox.y = path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();
		
		return canGo;
	}
		
	protected boolean goRight(float xDistance) {
		currentDirection = RIGHT;
		boolean canGo = true;

		if(Math.abs(xDistance) > speed) {
			tempHitboxForCheck.y = hitbox.y;
			tempHitboxForCheck.x = hitbox.x + speed;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x = tempHitboxForCheck.x;
			
			else 
				canGo = false;
			
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
			hitbox.x = path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();	
		
		return canGo;
	}

	protected boolean goLeft(float xDistance) {
		currentDirection = LEFT;
		boolean canGo = true;

		if(Math.abs(xDistance) > speed) {
			tempHitboxForCheck.y = hitbox.y;
			tempHitboxForCheck.x = hitbox.x - speed;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x = tempHitboxForCheck.x;
			
			else 
				canGo = false;
			
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
			hitbox.x = path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();
		
		return canGo;
	}

	
	public String getType() {
		return type;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public int getCurrentAction() {
		return currentAction;		
	}
	
	public int getCurrentDirection() {
		return currentDirection;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		String dataEntity =  "DataEntity" + hitbox.x + ", " + hitbox.y;
		return dataEntity;
	}
	
	public int getCurrenState() {
		return currentState;
	}
}
