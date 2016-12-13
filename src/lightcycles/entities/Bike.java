package lightcycles.entities;

public abstract class Bike extends GameObject {
	//	BIKE FEATURES
	protected float speed;
	protected int currdirection;
	
	protected final static int WIDTH = 100, HEIGHT = 200;
	
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
	}
	
	private void move() {
		switch (currdirection) {
		case 0:	//	SOUTH
			position.y += speed;	//	CHANGES SPEED
			break;
		case 1:	//	NORTH
			position.y -= speed;
			break;
		case 2:	//	WEST
			position.x -= speed;
			break;
		case 3:	//	EAST
			position.x += speed;
			break;
		}
	}
	
	public void update() {
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
		
		move();
	}
}
