package lightcycles.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import lightcycles.util.BufferUtil;

public class VertexArray {
	private int VAO, VBO, EBO;
	
	public VertexArray(float[] vertices, int[] indices) {
		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		EBO = glGenBuffers();
		
		glBindVertexArray(VAO);
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		//glBufferData(GL_ARRAY_BUFFER, BufferUtil.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		//	Position
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * (Float.SIZE / 8), 0);
		glEnableVertexAttribArray(0);
		//	Texture
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * (Float.SIZE / 8), 3 * (Float.SIZE / 8));
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		//glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtil.createByteBuffer(indices), GL_STATIC_DRAW);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		
		glBindVertexArray(0);
	}
	
	public void delete() {
		glDeleteVertexArrays(VAO);
		glDeleteBuffers(VBO);
		glDeleteBuffers(EBO);
	}
	
	public void render() {
		glBindVertexArray(VAO);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);
	}
}
