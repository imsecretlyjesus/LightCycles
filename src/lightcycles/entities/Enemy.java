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
	
	int counter = 0;
	
	//	random movement must be implemented -- AI
	public void update() {
		
		int j = (int)Math.random();
		
		for(int r = 0; r < 100000; r++){
			counter += 1;
		}
		
		
		if(j >= 0 && j < 0.1){
			if (counter == 1){
				mover();
			}
		}else if(j >= 0.1 && j < 0.2){
			if (counter == 2){
				mover();
				
			}
			counter = 0;
			
		}else if(j >= 0.2 && j < 0.3){
			if (counter == 3){
				mover();
				
			}
			counter = 0;
			
		}else if(j >= 0.3 && j < 0.4){
			if (counter == 4){
				mover();
				
			}
			counter = 0;
			
		}else if(j >= 0.4 && j < 0.5){
			if (counter == 5){
				mover();
				
			}
			counter = 0;
			
		}else if(j >= 0.5 && j < 0.6){
			if (counter == 6){
				mover();
				
			}
			counter = 0;
			
		}else if(j >= 0.6 && j < 0.7){
			if (counter == 7){
				mover();
				
			}
			counter = 0;
			
		}else if(j >= 0.7 && j < 0.8){
			if (counter == 8){
				mover();
				
			}
			counter = 0;
			
		}else if(j >= 0.8 && j < 0.9){
			if (counter == 9){
				mover();
				
			}
			counter = 0;
			
		}else{
			if (counter == 10){
				mover();
				
			}
			counter = 0;
			
		}
		
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
	
	public void mover(){
		
		int i = (int)Math.random();
		
		if(i >= 0 && i < 0.25){
			currdirection = 2;
		}else if(i >= 0.25 && i < 0.50){
			currdirection = 3;
		}else if(i >= 0.50 && i < 0.75){
			currdirection = 0;
		}else{
			currdirection = 1;
		}
	}
}
