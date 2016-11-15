package lightcycles;

import lightcycles.entities.*;
import lightcycles.gfx.*;
import lightcycles.input.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;	//	these imports are important to access certain OpenGL methods
import static org.lwjgl.system.MemoryUtil.*;

public class Game {
	
	//	The window handle
	private long window;
	private int WIDTH = 800, HEIGHT = 600;
	
	private GLFWKeyCallback keyCallback;	//	InputHandler
	
	private Shader ourProgram;	//	Shader Program
	
	private Player player;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		
		try {
			init();	//	Initialize GLFW and OpenGL context
			loop();	//	Game loop

			// Free the window callbacks and destroy the window
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
		} finally {
			// Terminate GLFW and free the error callback
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}
	}
	/*
	private void createGraphics() {
	}
	*/
	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		//	Init GLFW
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
	    //  Set all required options for GLFW
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
	    //  Mac OS specific
	    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		//	Create a GLFWwindow object that we can use for GLFW's functions
		window = glfwCreateWindow(WIDTH, HEIGHT, "Learn OpenGL", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		glfwMakeContextCurrent(window);
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		//	Setup a key callback. It will be called every time a key is pressed, repeated or released.
		//	Set the required callback function
		glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
		
		glViewport(0, 0, WIDTH, HEIGHT);
		
		//	Build and compile our shader program
		ourProgram = new Shader(
				"/Users/oshovalgas/Documents/java_workspace/git/LightCycles/src/lightcycles/gfx/shader.vs",
				"/Users/oshovalgas/Documents/java_workspace/git/LightCycles/src/lightcycles/gfx/shader.frag"
				);
		//createGraphics();
		
		player = new Player();
	}
	
	// UPDATES (PLAYER & INPUT SO FAR)
	private void update() {
		player.update();
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE))
			glfwSetWindowShouldClose(window, true);
	}
	
	private void loop() {
		//  Game loop
		while (!glfwWindowShouldClose(window)) {
	        //  Check if any events have been activated (key pressed, mouse moved etc.) and call corresponding response functions
	        glfwPollEvents();
	        update();
	        
	        //  Render
	        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
	        //  Clear the colorbuffer
	        glClear(GL_COLOR_BUFFER_BIT);
	        
	        //  Draw our triangle
	        ourProgram.use();
	        
	        player.render();
	        
	        //  Swap the screen buffers
	        glfwSwapBuffers(window);
		}
		//  Properly de-allocate all resources once they've outlived their purpose
		player.delete();
	}

	public static void main(String[] args) {
		new Game().run();
	}

}
