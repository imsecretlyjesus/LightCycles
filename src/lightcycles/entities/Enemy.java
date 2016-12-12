package lightcycles.entities;

import java.util.Random;

public class Enemy extends Bike {
	private Random random = new Random();
	private long elapseTime;
	private long lastTime;

	public Enemy() {
		super("res/crate-wood.png");		//	CHANGE FILE PATH
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
