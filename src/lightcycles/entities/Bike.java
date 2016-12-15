package lightcycles.entities;

import lightcycles.gfx.RenderEngine;
import lightcycles.math.Vector3f;

public abstract class Bike extends GameObject {
	//	BIKE FEATURES
	protected float speed;
	protected int currdirection;
	protected boolean changedDirection;
	
	private static final int SCALE = 1;
	protected static final int WIDTH = 23 * SCALE, HEIGHT = 50 * SCALE;
	
	protected LightCycle[] light_cycles;
	protected int index;
	protected Vector3f trail_color;
	
	
	public Bike() {
		this("res/box.png", 0, 0);
	}
	
	public Bike(int x, int y) {
		this("res/box.png", x, y);
	}
	
	protected Bike(String path, int x, int y) {
		super(path, x, y, WIDTH, HEIGHT);
		
		speed = 2.0f;
		currdirection = 0;
		
		light_cycles = new LightCycle[1000];
		index = 0;
		
		if(this instanceof Player) {
			//	blue
			trail_color = new Vector3f(0.0f, 153.0f / 255.0f, 153.0f / 255.0f);
		} else if (this instanceof Enemy) {
			//	red
			trail_color = new Vector3f(240.0f / 255.0f, 0.0f, 0.0f);
		} else {
			//	green -- player two
			trail_color = new Vector3f(32.0f / 255.0f, 192.0f / 255.0f, 44.0f / 255.0f);
		}
		
		generateLightCycle();
	}
	
	private void move() {
		switch (currdirection) {
			case 0:	//	DOWN
				position.y += speed;
				light_cycles[index].SIZE.y += speed;
				break;
			case 1:	//	UP
				position.y -= speed;
				light_cycles[index].SIZE.y += speed;
				break;
			case 2:	//	LEFT
				position.x -= speed;
				light_cycles[index].SIZE.y += speed;
				break;
			case 3:	//	RIGHT
				position.x += speed;
				light_cycles[index].SIZE.y += speed;
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
		light_cycles[index] = new LightCycle((int)position.x, (int)position.y, angle, trail_color);
		
		light_cycles[index].SIZE.x = SIZE.x;
		
		RenderEngine.configShader(
				light_cycles[index],
				RenderEngine.TRAIL_SHADER
				);
	}
	
	private class LightCycle extends GameObject {
		private Vector3f color;
		
		private LightCycle(int x, int y, float angle, Vector3f color) {
			super(x, y);
			this.angle = angle;
			this.color = color;
		}
		
		public void render() {
			RenderEngine.TRAIL_SHADER.setUniform3f("trail_color", color.x, color.y, color.z);
			super.render();
		}
	}
}
