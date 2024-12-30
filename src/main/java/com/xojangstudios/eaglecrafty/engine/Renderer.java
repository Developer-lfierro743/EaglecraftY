package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.xojangstudios.eaglecrafty.world.World;

public class Renderer {

    private final Cube cube;

    public Renderer() {
        // Initialize rendering context (OpenGL, shaders, etc.)
        initOpenGL();
        cube = new Cube();
    }

    private void initOpenGL() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        // Create a windowed mode window and its OpenGL context
        long window = GLFW.glfwCreateWindow(800, 600, "EaglecraftY", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // Enable vsync
        GLFW.glfwShowWindow(window);

        GL.createCapabilities(); // Initialize OpenGL capabilities

        // Check for GLFW errors
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer buffer = stack.mallocPointer(1);
            int errorCode = GLFW.glfwGetError(buffer);
            if (errorCode != GLFW.GLFW_NO_ERROR) {
                String description = MemoryUtil.memUTF8(buffer.get(0));
                throw new IllegalStateException("GLFW error: " + description);
            }
        }

        // Set the clear color to black
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Main loop for rendering
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // Clear the screen

            render(new World()); // Render the world (containing the cube)

            GLFW.glfwSwapBuffers(window); // Swap the buffers
            GLFW.glfwPollEvents(); // Poll for window events
        }

        // Cleanup before exit
        cube.cleanUp();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void render(World world) {
        // Here we could render other world objects in the future
        cube.render(); // Render the cube
    }
}