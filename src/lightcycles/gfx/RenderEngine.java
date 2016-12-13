package lightcycles.gfx;

import lightcycles.entities.GameObject;
import lightcycles.entities.GameObject;
import lightcycles.math.Matrix4f;

import static org.lwjgl.opengl.GL20.*;	//

import java.util.HashMap;
import java.util.Map;
	
public abstract class RenderEngine {
	private static Map<GameObject,Shader> renderHashMap = new HashMap<GameObject,Shader>();
	public static Matrix4f ortho;
	
	public static final Shader BIKE_SHADER = new Shader(
			"src/lightcycles/gfx/shaders/bike.vs",
			"src/lightcycles/gfx/shaders/bike.frag"
			);
	
	public static final Shader TRAIL_SHADER = new Shader(
			"src/lightcycles/gfx/shaders/trail.vs",
			"src/lightcycles/gfx/shaders/trail.frag"
			);
	
	private static Shader Active_Shader;
	
	public static Shader getActiveShader() {
		return Active_Shader;
	}
	
	public static void configShader(GameObject gameObject, Shader shader) {
		renderHashMap.put(gameObject, shader);
	}
	
	/**
	 * Deletes all Shader objects.
	 */
	public static void delete() {
		//	delete all shaders
		renderHashMap.values().forEach(s -> s.delete());
	}
	
	/**
	 * Deletes Shader object associated with given GameObject.
	 * @param gameObject
	 */
	public static void delete(GameObject gameObject) {
		renderHashMap.get(gameObject).delete();
	}
	
	/**
	 * Renders given GameObject.
	 * @param gameObject
	 */
	public static void render(GameObject gameObject) {
		Shader shader = renderHashMap.get(gameObject);
		
		shader.setUniformMatrix4fv("projection", ortho);
		shader.use();
		gameObject.render();
		
		glUseProgram(0);
	}
	
	/**
	 * Renders all GameObjects.
	 */
	public static void render() {
		renderHashMap.forEach((gameObject, shader) -> {
			shader.setUniformMatrix4fv("projection", ortho);
			shader.use();
			Active_Shader = shader;
			gameObject.render();
		});
		glUseProgram(0);
		Active_Shader = null;
	}
}
