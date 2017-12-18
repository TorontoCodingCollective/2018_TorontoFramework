package com.torontocodingcollective.oi;

public class TToggle {

	private boolean toggleState;
	private final TGameController gameController;
	private final TButton button;
	private boolean previousButtonState;
	
	public TToggle(TGameController gameController, TButton button) {
		this(gameController, button, false);
	}
	
	public TToggle(TGameController gameController, TButton button, boolean initialState) {
		this.gameController = gameController;
		this.button = button;
		this.toggleState = initialState;
		this.previousButtonState = gameController.getButton(button);
	}
	
	private boolean get(){
		return toggleState;
	}
	private void set(boolean set){
		toggleState = set;
	}
	private void updatePeriodic(){
		if (gameController.getButton(button) && !previousButtonState){
			toggleState = !toggleState;
		}
		previousButtonState = gameController.getButton(button);
	}
}
