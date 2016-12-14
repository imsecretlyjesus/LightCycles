package lightcycles.entities;

import lightcycles.gfx.RenderEngine;
import lightcycles.gfx.Texture;
import lightcycles.gfx.VertexArray;
import lightcycles.math.Matrix4f;
import lightcycles.math.Vector3f;

public class GameObject {
	protected Texture texture;
	protected VertexArray mesh;
	protected Vector3f position;
	protected Vector3f SIZE = new Vector3f(100, 100, 0); 
	protected float angle;
	
	public GameObject() {
		this(0, 0);
	}
	
	public GameObject(int x, int y) {
		this("res/box.png", x, y);
	}
	
	public GameObject(String path, int x, int y) {
		position = new Vector3f(x, y, 0);
		
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
		
		angle = 0.0f;
	}
	
	public void delete() {
		texture.delete();
		mesh.delete();
	}
	
	private void move() {
		
	}
	
	public void update() {
		move();
	}
	
	public int getX() {
		return (int)position.x;
	}
	
	public int getY() {
		return (int)position.y;
	}
	
	public int getWidth() {
		if (angle == 90 || angle == 270)
			return (int)SIZE.y;
		
		return (int)SIZE.x;
	}
	
	public int getHeight() {
		if (angle == 90 || angle == 270)
			return (int)SIZE.x;
		
		return (int)SIZE.y;
	}
	
	public void render() {
		Matrix4f transform;
		transform = Matrix4f.translate(position);
		transform = transform.multiply(Matrix4f.translate(new Vector3f(0.5f * SIZE.x, 0.5f * SIZE.y, 0.0f)));
		transform = transform.multiply(Matrix4f.rotate(angle));
		transform = transform.multiply(Matrix4f.translate(new Vector3f(-0.5f * SIZE.x, -0.5f * SIZE.y, 0.0f)));
		transform = transform.multiply(Matrix4f.scale(SIZE));
		
		texture.bind();
		RenderEngine.getActiveShader().setUniformMatrix4fv("model", transform);
		mesh.render();
		texture.unbind();
	}
}
