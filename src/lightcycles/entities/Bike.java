package lightcycles.entities;

import lightcycles.gfx.RenderEngine;

public abstract class Bike extends GameObject {
	//	BIKE FEATURES
	protected float speed;
	protected int currdirection;
	
	protected final static int WIDTH = 23, HEIGHT = 50;
	
	protected boolean changedDirection;
	protected LightCycle[] light_cycles;
	protected int index;
	
	
	public Bike() {
		this("res/blue_bike.png", 0, 0);
	}
	
	public Bike(int x, int y) {
		this("res/blue_bike.png", x, y);
	}
	
	public Bike(String path, int x, int y) {
		super(path, x, y);
		SIZE.x = (float)WIDTH;
		SIZE.y = (float)HEIGHT;
		
		speed = 2.0f;
		currdirection = 0;
		
		light_cycles = new LightCycle[1000];
		index = 0;
		
		generateLightCycle();
	}
	
	private void move() {
		switch (currdirection) {
		case 0:	//	SOUTH
			position.y += speed;	//	CHANGES SPEED
			light_cycles[index].SIZE.y += speed;
			break;
		case 1:	//	NORTH
			position.y -= speed;
			light_cycles[index].SIZE.y -= speed;
			break;
		case 2:	//	WEST
			position.x -= speed;
			light_cycles[index].SIZE.x -= speed;
			break;
		case 3:	//	EAST
			position.x += speed;
			light_cycles[index].SIZE.x += speed;
			break;
		}
	}
	
	public void update() {
		if (changedDirection) {
			switch(currdirection) {
				case 0:
					angle = 0.0f;
					break;
				case 1:
					angle = 180.0f;
					break;
				case 2:
					angle = 90.0f;
					break;
				case 3:
					angle = 270.0f;
					break;
			}
			index++;
			generateLightCycle();
		}
		changedDirection = false;
		
		move();
	}
	
	private void generateLightCycle() {
		light_cycles[index] = new LightCycle((int)position.x, (int)position.y);
		
		light_cycles[index].SIZE.x = SIZE.x;
		light_cycles[index].SIZE.y = SIZE.y;
		
		RenderEngine.configShader(
				light_cycles[index],
				RenderEngine.TRAIL_SHADER
				);
	}
	
	private class LightCycle extends GameObject {
		public LightCycle(int x, int y) {
			super(x, y);
		}
	}
}
