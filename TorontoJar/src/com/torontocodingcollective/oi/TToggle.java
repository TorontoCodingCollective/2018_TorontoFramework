package com.torontocodingcollective.oi;

public class TToggle {

	private boolean toggleState;
	private final TGameController gameController;
	private final TButton button;
	private final TStick  stick;
	private boolean previousButtonState;
	
	public TToggle(TGameController gameController, TButton button) {
		this(gameController, button, false);
	}
	
	public TToggle(TGameController gameController, TButton button, boolean initialState) {
		this.gameController = gameController;
		this.button = button;
		this.stick = null;
		this.toggleState = initialState;
		this.previousButtonState = gameController.getButton(button);
	}
	
	public TToggle(TGameController gameController, TStick stick) {
		this(gameController, stick, false);
	}
	
	public TToggle(TGameController gameController, TStick stick, boolean initialState) {
		this.gameController = gameController;
		this.button = null;
		this.stick = stick;
		this.toggleState = initialState;
		this.previousButtonState = gameController.getButton(stick);
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
		
		boolean curButtonState = false;
		
		if (button != null) {
			curButtonState = gameController.getButton(button);
		}
		if (stick != null) {
			curButtonState = gameController.getButton(stick);
		}
		
		if (curButtonState && !previousButtonState){
			toggleState = !toggleState;
		}
		previousButtonState = curButtonState;
	}
}
