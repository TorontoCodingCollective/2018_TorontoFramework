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
	
	/**
	 * Get the current state of the toggle
	 * @return {@code true} or {@code false}
	 */
	public boolean get(){
		return toggleState;
	}
	
	/**
	 * Set the current state of the toggle
	 * @param set value {@code true} or {@code false}
	 */
	public void set(boolean set){
		toggleState = set;
	}
	
	/**
	 * UpdatePeriodic
	 * <p>
	 * This routine must be called every loop in order to update the state of the 
	 * toggle based on the game controller and button.
	 */
	public void updatePeriodic(){
		if (gameController.getButton(button) && !previousButtonState){
			toggleState = !toggleState;
		}
		previousButtonState = gameController.getButton(button);
	}
}
