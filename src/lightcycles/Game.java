package lightcycles;

import lightcycles.Shaders.*;
import lightcycles.Input.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;	//	these imports are important to access certain OpenGL methods
import static org.lwjgl.opengl.GL15.*;	//
import static org.lwjgl.opengl.GL20.*;	//
import static org.lwjgl.opengl.GL30.*;	//
import static org.lwjgl.system.MemoryUtil.*;

public class Game {
	
	//	The window handle
	private long window;
	private int WIDTH = 800, HEIGHT = 600;
	
	private GLFWKeyCallback keyCallback;	//	InputHandler
	
	private Shader ourProgram;	//	Shader Program
	private int VAO, VBO;		//	Vertex Array Object, Vertex Buffer Object
	private float[] vertices;	//	Vertex attributes of graphics
	
	private int currdirection;

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
	
	private void createGraphics() {
	    //  Set up vertex data (and buffer(s)) and attribute pointers
	    vertices = new float[] {
	        -0.25f, -0.25f, 0.0f, //  Left
	         0.25f, -0.25f, 0.0f, //  Right
	         0.0f,   0.25f, 0.0f  //  Top
	    };
		
		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		//	Bind the Vertex Array Object first, then bind and set vertex buffer(s) and attribute pointer(s)
		glBindVertexArray(VAO);
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * ((Float.SIZE) / 8), 0);
		glEnableVertexAttribArray(0);
		
	    //  Note that this is allowed, the call to glVertexAttribPointer registered VBO as the currently bound vertex buffer object so afterwards we can safely unbind
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		//  Unbind VAO (it's always a good thing to unbind any buffer/array to prevent strange bugs)
		glBindVertexArray(0);
	}
	
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
				"PATH_TO/shader.vs",
				"PATH_TO/shader.frag"
				);
		createGraphics();
		
		// player direction
		currdirection = 0;	//	SOUTH
		
		//	0	SOUTH
		//	1	NORTH
		//	2	LEFT
		//	3	RIGHT
	}
	
	// UPDATES (INPUT SO FAR)
	private void update() {
		if (KeyboardHandler.isKeyDown(GLFW_KEY_LEFT) && currdirection != 3)
			currdirection = 2;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_RIGHT) && currdirection != 2)
			currdirection = 3;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_DOWN) && currdirection != 1)
			currdirection = 0;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_UP) && currdirection != 0)
			currdirection = 1;
		
		if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE))
			glfwSetWindowShouldClose(window, true);
		
		switch (currdirection) {
			case 0:	//	SOUTH
				for (int i = 1; i < vertices.length; i += 3)
					vertices[i] -= 0.01f;	//	CHANGES SPEED
				break;
			case 1:	//	NORTH
				for (int i = 1; i < vertices.length; i += 3)
					vertices[i] += 0.01f;
				break;
			case 2:	//	WEST
				for (int i = 0; i < vertices.length; i += 3)
					vertices[i] -= 0.01f;
				break;
			case 3:	//	EAST
				for (int i = 0; i < vertices.length; i += 3)
					vertices[i] += 0.01f;
				break;
		}
		
		glBindVertexArray(VAO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindVertexArray(0);
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
	        glBindVertexArray(VAO);
	        glDrawArrays(GL_TRIANGLES, 0, 3);
	        glBindVertexArray(0);
	        
	        //  Swap the screen buffers
	        glfwSwapBuffers(window);
		}
		//  Properly de-allocate all resources once they've outlived their purpose
		glDeleteVertexArrays(VAO);
		glDeleteBuffers(VBO);
	}

	public static void main(String[] args) {
		new Game().run();
	}

}
