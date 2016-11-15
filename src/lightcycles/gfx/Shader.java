package lightcycles.gfx;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;	//

public class Shader {
	private int Program;
	
	public Shader(String vertexPath, String fragmentPath) {
		try {
			String vShaderCode = readFile(
					vertexPath,
					Charset.forName("US-ASCII")
					);
			String fShaderCode = readFile(
					fragmentPath,
					Charset.forName("US-ASCII")
					);
			
			int vertex = glCreateShader(GL_VERTEX_SHADER);
			glShaderSource(vertex, vShaderCode);
			glCompileShader(vertex);
			
			int fragment = glCreateShader(GL_FRAGMENT_SHADER);
			glShaderSource(fragment, fShaderCode);
			glCompileShader(fragment);
			
			this.Program = glCreateProgram();
			glAttachShader(this.Program, vertex);
			glAttachShader(this.Program, fragment);
			glLinkProgram(this.Program);
			
			glDeleteShader(vertex);
			glDeleteShader(fragment);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getProgram() {
		return this.Program;
	}
	
	public void use() {
		glUseProgram(this.Program);
	}
	
	private static String readFile(String path, Charset encoding) 
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
