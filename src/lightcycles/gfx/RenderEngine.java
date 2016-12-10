package lightcycles.gfx;

import lightcycles.entities.Bike;

import static org.lwjgl.opengl.GL20.*;	//

import java.util.HashMap;
import java.util.Map;
	
public abstract class RenderEngine {
	public static Map<Bike,Shader> renderHashMap = new HashMap<Bike,Shader>();
	
	public static void delete() {
		//	delete all shaders
		renderHashMap.values().forEach(v -> v.delete());
	}
	
	public static void delete(Bike e) {
		renderHashMap.get(e).delete();
	}
	
	public static void render() {
		renderHashMap.forEach((k, v) -> {
			v.use();
			k.render();
		});
		glUseProgram(0);
	}
	
	public static void render(Bike e) {
		renderHashMap.get(e).use();
		e.render();
		glUseProgram(0);
	}
	
	public static void configShader(Bike e, String vertexPath, String fragmentPath) {
		renderHashMap.put(e, new Shader(vertexPath, fragmentPath));
	}
}
