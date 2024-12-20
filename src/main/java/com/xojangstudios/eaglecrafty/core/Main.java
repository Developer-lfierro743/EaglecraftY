package com.xojangstudios.eaglecrafty.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.Callbacks;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.init();
        game.run();
    }
}

class Game {

    private long window;

    public void run() {
        loop();

        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
    }

    public void init() {
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        // Create the window
        window = GLFW.glfwCreateWindow(800, 600, "EaglecraftY", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Make the window visible
        GLFW.glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    private void loop() {
        // Set the clear color
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // Swap the color buffers
            GLFW.glfwSwapBuffers(window);

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            GLFW.glfwPollEvents();

            // Update game logic
            updateGameLogic();

            // Update camera
            updateCamera();

            // Handle input
            handleInput();
        }
    }

    private void updateGameLogic() {
        // Implement game logic update here
    }

    private void updateCamera() {
        // Implement camera update logic here
    }

    private void handleInput() {
        // Implement input handling logic here
    }
}