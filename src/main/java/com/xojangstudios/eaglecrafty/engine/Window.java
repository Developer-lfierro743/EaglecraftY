package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class Window {

    private static long windowHandle;
        
            public Window(){
                if (!GLFW.glfwInit()) {
                    System.err.println("Failed to initialize GLFW.");
                    System.exit(-1);
                }
        
                windowHandle = GLFW.glfwCreateWindow(800, 600, "EaglecraftY", 0, 0);
                if (windowHandle == 0) {
                    System.err.println("Failed to create GLFW window.");
                    System.exit(-1);
                }
        
                GLFW.glfwMakeContextCurrent(windowHandle);
                GL.createCapabilities();
            }
    
            public static boolean ShouldClose() {
                return GLFW.glfwWindowShouldClose(windowHandle);
        }

        public static void swapBuffers() {
            GLFW.glfwSwapBuffers(windowHandle);
    }
}
