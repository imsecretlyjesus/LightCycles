package lightcycles.entities;

import lightcycles.gfx.Texture;

import static org.lwjgl.opengl.GL11.*;	//	these imports are important to access certain OpenGL methods
import static org.lwjgl.opengl.GL15.*;	//
import static org.lwjgl.opengl.GL20.*;	//
import static org.lwjgl.opengl.GL30.*;	//

public abstract class Bike {
	protected Texture texture;
	protected int VAO, VBO, EBO;
	protected float[] vertices;
	protected float speed;
	protected int currdirection;
	
	public Bike() {
		//	Generic
		this("path_to/bike.png");		//	CHANGE FILE PATH
	}
	
	public Bike(String path) {
		texture = new Texture(path);
		vertices = new float[] {
				//	Position			//	Color			//	Texture Coord
				 0.25f,  0.25f, 0.0f,	1.0f, 0.0f, 0.0f,	1.0f, 1.0f,	//	Top	Right
				 0.25f, -0.25f, 0.0f,	0.0f, 1.0f, 0.0f,	1.0f, 0.0f,	//	Bottom Right
				-0.25f, -0.25f, 0.0f,	0.0f, 0.0f, 1.0f,	0.0f, 0.0f,	//	Bottom Left
				-0.25f,  0.25f, 0.0f,	1.0f, 1.0f, 0.0f,	0.0f, 1.0f	//	Top	Left
		};
		speed = 0.005f;
		currdirection = 0;
		
		init();
	}
	
	public void delete() {
		glDeleteVertexArrays(VAO);
		glDeleteBuffers(VBO);
		glDeleteBuffers(EBO);
		glDeleteTextures(texture.getTexture());
	}
	
	private void init() {
		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		EBO = glGenBuffers();
		int[] indices = {
				0, 1, 3,	//	Right
				1, 2, 3		//	Left
		};
		
		glBindVertexArray(VAO);
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		//	Position
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * (Float.SIZE / 8), 0);
		glEnableVertexAttribArray(0);
		//	Color
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * (Float.SIZE / 8), 3 * (Float.SIZE / 8));
		glEnableVertexAttribArray(1);
		//	Texture
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * (Float.SIZE / 8), 6 * (Float.SIZE / 8));
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		
		glBindVertexArray(0);
	}
	
	private void move() {
		switch (currdirection) {
		case 0:	//	SOUTH
			for (int i = 1; i < vertices.length; i += 8)
				vertices[i] -= speed;	//	CHANGES SPEED
			break;
		case 1:	//	NORTH
			for (int i = 1; i < vertices.length; i += 8)
				vertices[i] += speed;
			break;
		case 2:	//	WEST
			for (int i = 0; i < vertices.length; i += 8)
				vertices[i] -= speed;
			break;
		case 3:	//	EAST
			for (int i = 0; i < vertices.length; i += 8)
				vertices[i] += speed;
			break;
		}
		
		bufferData();
	}
	
	private void bufferData() {
		glBindVertexArray(VAO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindVertexArray(0);
	}
	
	public void update() {
		move();
	}
	
	public void render() {
		glBindTexture(GL_TEXTURE_2D, texture.getTexture());
		glBindVertexArray(VAO);
		
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindVertexArray(0);
	}
}
