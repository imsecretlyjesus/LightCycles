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
	protected Vector3f SIZE;
	protected float angle;
	protected Matrix4f transform = new Matrix4f();
	
	public GameObject() {
		this(0, 0);
	}
	
	public GameObject(int x, int y) {
		this("res/box.png", x, y, 100, 100);
	}
	
	public GameObject(String path, int x, int y) {
		this(path, x, y, 100, 100);
	}
	
	public GameObject(String path, int x, int y, int width, int height) {
		position = new Vector3f(x, y, 0);
		SIZE = new Vector3f(width, height, 0);
		
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
		return (int)SIZE.x;
	}
	
	public int getHeight() {
		return (int)SIZE.y;
	}
	
	public void render() {
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
