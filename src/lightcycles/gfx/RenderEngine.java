package lightcycles.gfx;

import lightcycles.entities.Bike;
import lightcycles.math.Matrix4f;

import static org.lwjgl.opengl.GL20.*;	//

import java.util.HashMap;
import java.util.Map;
	
public abstract class RenderEngine {
	public static Map<Bike,Shader> renderHashMap = new HashMap<Bike,Shader>();
	public static Matrix4f ortho;
	
	public static final Shader BIKE_SHADER = new Shader(
			"src/lightcycles/gfx/shaders/bike.vs",
			"src/lightcycles/gfx/shaders/bike.frag"
			);
	
	public static void delete() {
		//	delete all shaders
		renderHashMap.values().forEach(v -> v.delete());
	}
	
	public static void delete(Bike e) {
		renderHashMap.get(e).delete();
	}
	
	/**
	 * Renders all existing entities.
	 */
	public static void render() {
		renderHashMap.forEach((k, v) -> {
			v.setUniformMatrix4fv("projection", ortho);
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
	
	public static void configShader(Bike e, Shader shader) {
		renderHashMap.put(e, shader);
	}
}
