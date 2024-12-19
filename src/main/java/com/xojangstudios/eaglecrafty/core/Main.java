package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.debug.DebugOverlay;
import com.xojangstudios.eaglecrafty.world.World;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {

    // Window handle
    private long window;


    // Debug overlay
    private DebugOverlay debugOverlay;

    // Build type (Developer, Pre-release, Stable)
    private static final String BUILD_TYPE = "Developer"; // Change this for different builds

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        System.out.println("Welcome to EaglecraftY!");
        init();
        loop();
        // Free resources and terminate GLFW
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private void init() {
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Create the window
        window = GLFW.glfwCreateWindow(800, 600, "EaglecraftY", 0, 0);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();


        // Initialize the debug overlay (only in developer builds)
        if (BUILD_TYPE.equals("Developer")) {
            debugOverlay = new DebugOverlay();
        }
    }

    private void loop() {
        // Run the rendering loop until the user closes the window
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Update the debug overlay
            if (debugOverlay != null) {
                debugOverlay.update();
            }

            // Clear the screen
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


            // Render the debug overlay
            if (debugOverlay != null) {
                debugOverlay.render();
            }

            GLFW.glfwSwapBuffers(window); // Swap the color buffers
            GLFW.glfwPollEvents();
        }
    }
}