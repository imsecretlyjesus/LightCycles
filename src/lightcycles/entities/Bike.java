package lightcycles.entities;

import lightcycles.gfx.RenderEngine;
import lightcycles.gfx.Texture;
import lightcycles.gfx.VertexArray;
import lightcycles.math.Matrix4f;
import lightcycles.math.Vector3f;

public abstract class Bike {
	protected Texture texture;
	protected VertexArray mesh;
	protected Vector3f position;
	//	BIKE FEATURES
	protected float speed;
	protected int currdirection;
	
	public Bike() {
		//	Generic
		this("res/bike.png");
	}
	
	public Bike(String path) {
		position = new Vector3f();
		
		float[] vertices = new float[] {
				//	Position	//	Texture Coord
				 0.25f,  0.25f, 0.0f, 1.0f, 1.0f,	//	Top	Right
				 0.25f, -0.25f, 0.0f, 1.0f, 0.0f,	//	Bottom Right
				-0.25f, -0.25f, 0.0f, 0.0f, 0.0f,	//	Bottom Left
				-0.25f,  0.25f, 0.0f, 0.0f, 1.0f	//	Top	Left
		};
		int[] indices = new int[] {
				0, 1, 3,
				1, 2, 3
		};
		
		mesh = new VertexArray(vertices, indices);
		texture = new Texture(path);
		
		speed = 0.005f;
		currdirection = 0;
	}
	
	public void delete() {
		texture.delete();
		mesh.delete();
	}
	
	private void move() {
		switch (currdirection) {
		case 0:	//	SOUTH
			position.y -= speed;	//	CHANGES SPEED
			break;
		case 1:	//	NORTH
			position.y += speed;
			break;
		case 2:	//	WEST
			position.x -= speed;
			break;
		case 3:	//	EAST
			position.x += speed;
			break;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void update() {
		move();
	}
	
	public void render() {
		texture.bind();
		RenderEngine.renderHashMap.get(this).setUniformMatrix4fv("ml_matrix", Matrix4f.translate(position));
		mesh.render();
		texture.unbind();
	}
}
