package lightcycles.gfx;

import lightcycles.entities.Bike;

import static org.lwjgl.opengl.GL11.*;	//	these imports are important to access certain OpenGL methods
import static org.lwjgl.opengl.GL15.*;	//
import static org.lwjgl.opengl.GL20.*;	//
import static org.lwjgl.opengl.GL30.*;	//
	
public class RenderEngine {
	//private RenderableObject[] objects;
	private int renderCount;
	private int[] VAOs;
	private int[] VBOs;
	private int[] EBOs;
	
	public RenderEngine() {
		//objects = new RenderableObject[5];
		VAOs = new int[5];
		VBOs = new int[5];
		EBOs = new int[5];
		renderCount = 0;
	}
	
	public void delete() {
		/*
		for (int i = 0; i < renderCount; i++)
			objects[i].delete();
		*/
		for (int i = 0; i < renderCount; i++) {
			glDeleteVertexArrays(VAOs[i]);
			glDeleteBuffers(VBOs[i]);
			glDeleteBuffers(EBOs[i]);
		}
	}
	
	public void render(Bike e) {
		/*
		RenderableObject renderableobject = objects[e.getID()];
		renderableobject.bufferData();
		
		glBindVertexArray(objects[e.getID()].getVAO());
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);
		*/
		glBindVertexArray(VAOs[e.getID()]);
		glBufferData(GL_ARRAY_BUFFER, e.getData(), GL_STATIC_DRAW);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);
	}
	
	private void doubleBuffers() {
		int[] newVAOs = new int[renderCount * 2];
		int[] newVBOs = new int[renderCount * 2];
		int[] newEBOs = new int[renderCount * 2];
		for (int i = 0; i < renderCount; i++) {
			newVAOs[i] = VAOs[i];
			newVBOs[i] = VBOs[i];
			newEBOs[i] = EBOs[i];
		}
	}
	
	/*
	private void doubleObjects() {
		RenderableObject[] newObjectArr = new RenderableObject[objects.length * 2];
		for (int i = 0; i < renderCount; i++)
			newObjectArr[i] = objects[i];
	}
	*/
	
	public void setGraphics(Bike e) {
		/*
		if (renderCount > objects.length)	doubleObjects();
		
		objects[renderCount++] = new RenderableObject(e);
		*/
		if (renderCount > VAOs.length)	doubleBuffers();
		
		int VAO = VAOs[renderCount] = glGenVertexArrays();
		int VBO = VBOs[renderCount] = glGenBuffers();
		int EBO = EBOs[renderCount] = glGenBuffers();
		
		glBindVertexArray(VAO);
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, e.getData(), GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * (Float.SIZE / 8), 0);
		glEnableVertexAttribArray(0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, new int[] {0, 1, 3, 1, 2, 3}, GL_STATIC_DRAW);
		
		glBindVertexArray(0);
		
		renderCount++;
	}
	
	private class RenderableObject {
		private Bike e;
		//private Texture texture;
		private int VAO, VBO, EBO;
		
		public RenderableObject(Bike e) {
			this.e = e;
			//texture = new Texture("");
			VAO = glGenVertexArrays();
			VBO = glGenBuffers();
			EBO = glGenBuffers();
			
			init();
		}
		
		private void init() {
			glBindVertexArray(VAO);
			
			glBindBuffer(GL_ARRAY_BUFFER, VBO);
			glBufferData(GL_ARRAY_BUFFER, e.getData(), GL_STATIC_DRAW);
			
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * (Float.SIZE / 8), 0);
			glEnableVertexAttribArray(0);
			
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, new int[] {0, 1, 3, 1, 2, 3}, GL_STATIC_DRAW);
			
			glBindVertexArray(VAO);
		}
		
		public void bufferData() {
			glBindVertexArray(VAO);
			glBufferData(GL_ARRAY_BUFFER, e.getData(), GL_STATIC_DRAW);
			glBindVertexArray(0);
		}
		
		public int getVAO() {
			return VAO;
		}
		
		public void delete() {
			glDeleteVertexArrays(VAO);
			glDeleteBuffers(VBO);
			glDeleteBuffers(EBO);
		}
	}
}
