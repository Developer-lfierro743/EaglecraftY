package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

public class Window {
    private static long windowHandle;

    public static void init() {
        // Set up an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        // Create the window
        windowHandle = GLFW.glfwCreateWindow(800, 600, "EaglecraftY", MemoryUtil.NULL, MemoryUtil.NULL);
        if (windowHandle == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        GLFW.glfwSwapInterval(1);
        // Make the window visible
        GLFW.glfwShowWindow(windowHandle);

        // Initialize OpenGL capabilities
        GL.createCapabilities();
    }

    public static boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public static void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    public static void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public static void terminate() {
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}