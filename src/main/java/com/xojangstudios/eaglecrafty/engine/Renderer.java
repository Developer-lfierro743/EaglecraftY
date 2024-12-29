package com.xojangstudios.eaglecrafty.engine;

import com.xojangstudios.eaglecrafty.world.World;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private int width = 800;
    private int height = 600;
    private long window;

    public Renderer(){
        // Initialize renderering context (opengl,shaders,etc)
        initOpenGL();
    }

    private void initOpenGL(){
    // Create a windowed mode window and make the OpenGL context current
    window = GLFW.glfwCreateWindow(width, height, "Eaglecrafty", 0, 0);
    if(window == 0){
        throw new RuntimeException("Failed to create the GLFW window");
    }

    GLFW.glfwMakeContextCurrent(window);
    GLFW.glfwSwapInterval(1);
    GLFW.glfwShowWindow(window);

    // initialize OpenGL
    GL.createCapabilities();

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f); //set the clear color to black
}

    //Method to render the game world
    public void render(World world){
        //main render loop
        while(!GLFW.glfwWindowShouldClose(window)){
            //clear the screen (color and depth buffer)
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // render the world
            renderWorld(world);

            // swap the buffer
            GLFW.glfwSwapBuffers(window);

            //poll for window events
            GLFW.glfwPollEvents();
        }
    }
    
    //Method to render the game world in this case, it will render the world's chunks
    private void renderWorld(World world){
        System.out.println("Rendering world...");
        // example of rendering a simple shape
        // render terrain,entities, and other objects (implement later)

        GL11.glVertex2f(-0.5f, -0.5f); // bottom left vertex
        GL11.glVertex2f(0.5f, -0.5f); // bottom right vertex
        GL11.glVertex2f(0.5f, 0.5f); // top right vertex
        GL11.glVertex2f(-0.5f, 0.5f); // top left vertex
        GL11.glVertex2f(-0.5f, -0.5f); // bottom left vertex
        glEnd(); // end rendering quads
    }
    //clean up Opengl resources when closing
    public void cleanup(){
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}