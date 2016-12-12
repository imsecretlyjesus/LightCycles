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
	protected float angle;
	
	private final static int WIDTH = 300, HEIGHT = 300;
	private final static Vector3f SIZE = new Vector3f(WIDTH, HEIGHT, 1);
	
	public Bike() {
		//	Generic
		this("res/bike.png");
	}
	
	public Bike(String path) {
		position = new Vector3f();
		
		float[] vertices = new float[] {
				//	Position	//	Texture Coord
				 0.0f, 0.0f, 0.0f, 1.0f,	//	Top	Left
				 1.0f, 0.0f, 1.0f, 1.0f,	//	Top Right
				 1.0f, 1.0f, 1.0f, 0.0f,	//	Bottom Right
				 0.0f, 1.0f, 0.0f, 0.0f		//	Bottom Left
		};
		int[] indices = new int[] {
				0, 1, 3,
				1, 2, 3
		};
		
		mesh = new VertexArray(vertices, indices);
		texture = new Texture(path);
		
		speed = 1.0f;
		currdirection = 0;
	}
	
	public void delete() {
		texture.delete();
		mesh.delete();
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
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
				angle = 270.0f;
				break;
			case 3:
				angle = 90.0f;
				break;
		}
		
		move();
	}
	
	public void render() {
		Matrix4f transform;
		transform = Matrix4f.translate(position);;
		transform = transform.multiply(Matrix4f.translate(new Vector3f(0.5f * SIZE.x, 0.5f * SIZE.y, 0.0f)));
		transform = transform.multiply(Matrix4f.rotate(angle));
		transform = transform.multiply(Matrix4f.translate(new Vector3f(-0.5f * SIZE.x, -0.5f * SIZE.y, 0.0f)));
		transform = transform.multiply(Matrix4f.scale(SIZE));
		
		texture.bind();
		RenderEngine.BIKE_SHADER.setUniformMatrix4fv("model", transform);
		mesh.render();
		texture.unbind();
	}
}
