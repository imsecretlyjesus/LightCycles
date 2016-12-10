package lightcycles.entities;

import static org.lwjgl.glfw.GLFW.*;

import lightcycles.input.KeyboardHandler;

public class Player extends Bike {
	public Player() {
		super("res/crate-wood.png");	//	CHANGE FILE PATH
	}
	
	public void update() {
		if (KeyboardHandler.isKeyDown(GLFW_KEY_A) && currdirection != 3)
			currdirection = 2;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_D) && currdirection != 2)
			currdirection = 3;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_S) && currdirection != 1)
			currdirection = 0;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_W) && currdirection != 0)
			currdirection = 1;
		
		super.update();
	}
}
