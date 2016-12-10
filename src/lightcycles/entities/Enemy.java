package lightcycles.entities;

import static org.lwjgl.glfw.GLFW.*;

import lightcycles.input.KeyboardHandler;

public class Enemy extends Bike {

	public Enemy() {
		super("res/enemy.png");		//	CHANGE FILE PATH
	}
	
	//	random movement must be implemented -- AI
	public void update() {
		//	implement AI here
		//	...
		if (KeyboardHandler.isKeyDown(GLFW_KEY_LEFT) && currdirection != 3)
			currdirection = 2;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_RIGHT) && currdirection != 2)
			currdirection = 3;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_DOWN) && currdirection != 1)
			currdirection = 0;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_UP) && currdirection != 0)
			currdirection = 1;
		
		super.update();
	}
}
