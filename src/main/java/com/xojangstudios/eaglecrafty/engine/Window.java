package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

public class Window {
    private static long window;
    private static int width = 800;
    private static int height = 600;
    private static final String TITLE = "EaglecraftY";

    public static void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        // Create the window
        window = GLFW.glfwCreateWindow(width, height, TITLE, 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(window, pWidth, pHeight);
            int windowWidth = pWidth.get(0);
            int windowHeight = pHeight.get(0);
            GLFW.glfwSetWindowPos(
                window,
                (GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).width() - windowWidth) / 2,
                (GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).height() - windowHeight) / 2
            );
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        // Enable v-sync
        GLFW.glfwSwapInterval(1);
        // Make the window visible
        GLFW.glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's OpenGL context, or any context that is managed externally.
        GL.createCapabilities();

        // Set the clear color
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public static void setSize(int width, int height) {
        Window.width = width;
        Window.height = height;
        GLFW.glfwSetWindowSize(window, width, height);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static long getWindow() {
        return window;
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
            window = 0; // Reset the window handle
        }
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}