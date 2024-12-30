package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.joml.Matrix4f;

import com.xojangstudios.eaglecrafty.world.World;

public class Renderer {
    private final Shader shader;
    private final Cube cube;
    private long window;
    private final Matrix4f view;
    private final Matrix4f projection;

    public Renderer() {
        // Initialize rendering context (OpenGL, shaders, etc.)
        initOpenGL();
        shader = new Shader(
            "/home/lfierro743/GameProjects/EaglecraftY/src/resources/shaders/vertex_shader.glsl",
            "/home/lfierro743/GameProjects/EaglecraftY/src/resources/shaders/fragment_shader.glsl"
        );
        cube = new Cube();
        view = new Matrix4f().lookAt(0, 0, 3, 0, 0, 0, 0, 1, 0);
        projection = new Matrix4f().perspective((float) Math.toRadians(45.0f), 800.0f / 600.0f, 0.1f, 100.0f);
    }

    private void initOpenGL() {
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
    }

    public void render(World world) {
        // Clear the screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Use the shader program
        shader.use();

        // Set the view and projection matrices
        shader.setMat4("view", view);
        shader.setMat4("projection", projection);

        // Render the world (e.g., call cube.render())
        cube.render();

        // Swap the buffers
        GLFW.glfwSwapBuffers(window);

        // Poll for window events
        GLFW.glfwPollEvents();
    }

    public void cleanup() {
        // Destroy the window and terminate GLFW
        if (window != 0) {
            GLFW.glfwDestroyWindow(window);
            window = 0; // Mark the window as destroyed
        }
        GLFW.glfwTerminate();
        cube.cleanUp();
    }
}