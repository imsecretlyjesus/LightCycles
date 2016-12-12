package lightcycles.entities;

import java.util.Random;

import lightcycles.Game;

public class Enemy extends Bike {
	private Random random = new Random();
	private long elapseTime;
	private long lastTime;
	private boolean go_[];
	
	public Enemy() {
		this(0,0);
	}
	
	public Enemy(int x, int y) {
		super("res/crate-wood.png", x, y);		//	CHANGE FILE PATH
		elapseTime = random.nextInt(1500) + 500;
		lastTime = System.currentTimeMillis();
	}
	
	//	random movement must be implemented -- AI
	public void update() {
		//	implement AI here
		//	...
		
		if (System.currentTimeMillis() - lastTime > elapseTime) {
			if (currdirection == 0 || currdirection == 1)
				currdirection = random.nextInt(2) + 2;
			else
				currdirection = random.nextInt(2);
			
			elapseTime = random.nextInt(1500) + 500;
			lastTime = System.currentTimeMillis();
		}
		
		super.update();
	}
}
