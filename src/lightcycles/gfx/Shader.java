package lightcycles.gfx;

import java.util.HashMap;
import java.util.Map;

import lightcycles.math.Matrix4f;
import lightcycles.util.ShaderUtil;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
	private final int Program;
	private Map<String,Integer> locationCache = new HashMap<String,Integer>();
	
	public Shader(String vertexPath, String fragmentPath) {
		Program = ShaderUtil.createProgram(vertexPath, fragmentPath);
	}
	
	private int getUniform(String name) {
		if (locationCache.containsKey(name))
			return locationCache.get(name);
		
		int result = glGetUniformLocation(this.Program, name);
		if (result == -1)
			System.err.println("Could not find uniform variable '" + name + "'!");
		else
			locationCache.put(name, result);
		
		return result;
	}
	
	public void setUniformMatrix4fv(String name, Matrix4f matrix) {
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void delete() {
		glDeleteProgram(this.Program);
	}
	
	public void use() {
		glUseProgram(this.Program);
	}
}
