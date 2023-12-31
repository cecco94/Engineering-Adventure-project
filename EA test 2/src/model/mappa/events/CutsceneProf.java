package model.mappa.events;

import controller.main.Gamestate;
import controller.playState.Hitbox;
import model.IModel;

public class CutsceneProf extends Event {

	public CutsceneProf(Hitbox r, IModel m, int i) {
		super(r, m, i);
	}

	@Override
	public void interact() {
		//potremmo mettere la musica del combattimento
		model.getController().setGameState(Gamestate.BOSS_CUTSCENE);
		endInteraction = true;
	}

}
