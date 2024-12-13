package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.rendering.Renderer;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Game {
    private final long window;
    private final int width = 800;
    private final int height = 600;
    private final String title = "EaglecraftY";

    private Renderer renderer;
    private Camera camera;

    private double lastMouseX = width / 2.0; // Center of the window
    private double lastMouseY = height / 2.0; // Center of the window
    private boolean firstMouse = true;

    public Game() {
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            System.err.println("Failed to initialize GLFW");
            System.exit(1);
        }

        // Create a window
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            System.err.println("Failed to create window");
            System.exit(1);
        }

        // Set up OpenGL
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);

        // Initialize the Renderer
        renderer = new Renderer();

        // Initialize the Camera
        camera = new Camera(new Vector3f(0, 0, 5));

        // Set up input handling
        GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
                    float moveSpeed = 0.1f;

                    if (key == GLFW.GLFW_KEY_W) {
                        camera.moveForward(moveSpeed);
                    } else if (key == GLFW.GLFW_KEY_S) {
                        camera.moveBackward(moveSpeed);
                    } else if (key == GLFW.GLFW_KEY_A) {
                        camera.moveLeft(moveSpeed);
                    } else if (key == GLFW.GLFW_KEY_D) {
                        camera.moveRight(moveSpeed);
                    } else if (key == GLFW.GLFW_KEY_SPACE) {
                        camera.moveUp(moveSpeed);
                    } else if (key == GLFW.GLFW_KEY_LEFT_SHIFT) {
                        camera.moveDown(moveSpeed);
                    }
                }
            }
        });

        // Set up mouse input
        GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double mouseX, double mouseY) {
                if (firstMouse) {
                    lastMouseX = mouseX;
                    lastMouseY = mouseY;
                    firstMouse = false;
                }

                // Calculate mouse movement delta
                double deltaX = mouseX - lastMouseX;
                double deltaY = lastMouseY - mouseY; // Reversed since y-coordinates go from bottom to top

                // Update last mouse position
                lastMouseX = mouseX;
                lastMouseY = mouseY;

                // Adjust sensitivity
                float sensitivity = 0.1f;
                deltaX *= sensitivity;
                deltaY *= sensitivity;

                // Update camera rotation
                camera.rotate((float) deltaY, (float) deltaX);
            }
        });

        // Hide and lock the mouse cursor to the center of the window
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
    }

    public void run() {
        // Main game loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Clear the screen
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Pass the camera's view and projection matrices to the Renderer
            renderer.render(camera.getViewMatrix(), camera.getProjectionMatrix());

            // Swap buffers and poll events
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        // Clean up
        GLFW.glfwTerminate();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}