package lightcycles.util;

import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ShaderUtil {
	public static int createProgram(String vertexPath, String fragmentPath) {
		int program = glCreateProgram();
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
			//	VERTEX COMPILE STATUS
			if (glGetShaderi(vertex, GL_COMPILE_STATUS) != 1)
				System.err.println("ERROR::SHADER::VERTEX::COMPILE_FAILURE\n" + glGetShaderInfoLog(vertex));
			
			int fragment = glCreateShader(GL_FRAGMENT_SHADER);
			glShaderSource(fragment, fShaderCode);
			glCompileShader(fragment);
			//	FRAGMENT COMPILE STATUS
			if (glGetShaderi(fragment, GL_COMPILE_STATUS) != 1)
				System.err.println("ERROR::SHADER::FRAGMENT::COMPILE_FAILURE\n" + glGetShaderInfoLog(vertex));
			
			//	program created
			glAttachShader(program, vertex);
			glAttachShader(program, fragment);
			glLinkProgram(program);
			//	PROGRAM LINK STATUS
			if (glGetProgrami(program, GL_LINK_STATUS) != 1)
				System.err.println("ERROR::SHADER::PROGRAM::LINK_FAILURE\n" + glGetProgramInfoLog(vertex));
			
			glDeleteShader(vertex);
			glDeleteShader(fragment);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return program;
	}
	
	private static String readFile(String path, Charset encoding) 
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
