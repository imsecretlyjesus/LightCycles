package lightcycles.gfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.*;

import static org.lwjgl.opengl.GL11.*;	//	these imports are important to access certain OpenGL methods
import static org.lwjgl.opengl.GL12.*;	//
import static org.lwjgl.opengl.GL30.*;	//

public class Texture {
	private int texture;
	private LoadableImageData image;
	
	public Texture(String path) {
		image = new PNGImageData();
		try {
			image.loadImage(new FileInputStream(new File(path)));
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image.getTexWidth(), image.getTexHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, image.getImageBufferData());
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
