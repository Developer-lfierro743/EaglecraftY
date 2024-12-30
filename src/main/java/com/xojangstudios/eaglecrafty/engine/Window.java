package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.glfw.GLFW;

public class Window {
    private static long window;

    public static void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        // Create a windowed mode window and its OpenGL context
        window = GLFW.glfwCreateWindow(800, 600, "EaglecraftY", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // Enable vsync
        GLFW.glfwShowWindow(window);
    }

    public static boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public static void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public static void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public static void terminate() {
        if (window != 0) {
            GLFW.glfwDestroyWindow(window);
            window = 0; // Mark the window as destroyed
        }
        GLFW.glfwTerminate();
    }
}