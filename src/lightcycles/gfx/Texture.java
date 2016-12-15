package lightcycles.gfx;

import static org.lwjgl.opengl.GL11.*;	//	these imports are important to access certain OpenGL methods
import static org.lwjgl.opengl.GL30.*;	//
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;

public class Texture {
	private int texture;
	ByteBuffer image;
	private final int WIDTH, HEIGHT;
	
	public Texture(String path) {
		int[] width = new int[1], height = new int[1], comp = new int[1];
		
		stbi_set_flip_vertically_on_load(true);
		image = stbi_load(path, width, height, comp, 4);
		
		WIDTH = width[0];
		HEIGHT = height[0];
		
		init();
		
		stbi_image_free(image);
	}
	
	private void init() {
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		//	texture parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		//	texture filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		//	image loaded
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, WIDTH, HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);	//	unbind texture
	}
	
	public void delete() {
		glDeleteTextures(texture);
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texture);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getTexture() {
		return texture;
	}
}
