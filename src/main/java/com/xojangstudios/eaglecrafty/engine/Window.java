package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Window {
    private static long windowHandle;
    private static int width = 800; // Default width
    private static int height = 600; // Default height

    public static void init(int width, int height, String title) {
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
        windowHandle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (windowHandle == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if (vidMode != null) {
            GLFW.glfwSetWindowPos(
                windowHandle,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
            );
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        GLFW.glfwSwapInterval(1);
        // Make the window visible
        GLFW.glfwShowWindow(windowHandle);

        // Initialize OpenGL capabilities
        GL.createCapabilities();

        // Set up a window resize callback
        GLFW.glfwSetFramebufferSizeCallback(windowHandle, (window, newWidth, newHeight) -> {
            Window.width = newWidth;
            Window.height = newHeight;
            GL11.glViewport(0, 0, newWidth, newHeight);
        });
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

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}