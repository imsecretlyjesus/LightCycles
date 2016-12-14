package lightcycles.entities;

import static org.lwjgl.glfw.GLFW.*;

import lightcycles.input.KeyboardHandler;

public class Player extends Bike {
	public Player() {
		this(0, 0);
	}
	
	public Player(int x, int y) {
		super("res/blue_bike.png", x, y);
		speed = 1.0f;
	}
	
	public void update() {
		if (currdirection != 0 && currdirection != 1) {
			if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
				currdirection = 1;
				changedDirection = true;
			}
			else if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
				currdirection = 0;
				changedDirection = true;
			}
		} else {
			if (KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
				currdirection = 2;
				changedDirection = true;
			}
			
			if (KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
				currdirection = 3;
				changedDirection = true;
			}
		}
		
		super.update();
	}
}
