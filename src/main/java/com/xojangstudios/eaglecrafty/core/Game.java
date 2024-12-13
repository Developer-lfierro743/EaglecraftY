package com.xojangstudios.eaglecrafty.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.xojangstudios.eaglecrafty.rendering.Renderer;

@SuppressWarnings("unused")
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
    private boolean mouseLocked = true; // Tracks whether the mouse is locked

    private int crosshairVao;
    private int crosshairVbo;
    private int crosshairShaderProgram;

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

                    // Handle camera movement
                    switch (key) {
                        case GLFW.GLFW_KEY_W -> camera.moveForward(moveSpeed);
                        case GLFW.GLFW_KEY_S -> camera.moveBackward(moveSpeed);
                        case GLFW.GLFW_KEY_A -> camera.moveLeft(moveSpeed);
                        case GLFW.GLFW_KEY_D -> camera.moveRight(moveSpeed);
                        case GLFW.GLFW_KEY_SPACE -> camera.moveUp(moveSpeed);
                        case GLFW.GLFW_KEY_LEFT_SHIFT -> camera.moveDown(moveSpeed);
                        default -> {
                        }
                    }

                    // Handle mouse lock/unlock
                    if (key == GLFW.GLFW_KEY_Z) {
                        mouseLocked = !mouseLocked; // Toggle mouse lock state
                        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR,
                                mouseLocked ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
                    }
                }
            }
        });

        // Set up mouse input
        GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double mouseX, double mouseY) {
                if (mouseLocked) { // Only process mouse movement if the mouse is locked
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
            }
        });

        // Hide and lock the mouse cursor to the center of the window initially
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        // Initialize the crosshair
        initCrosshair();
    }

    private void initCrosshair() {
        // Create a simple crosshair using two lines
        float[] crosshairVertices = {
            // Horizontal line
            -0.01f, 0.0f, 0.0f, // Left point
            0.01f, 0.0f, 0.0f,  // Right point

            // Vertical line
            0.0f, -0.01f, 0.0f, // Bottom point
            0.0f, 0.01f, 0.0f   // Top point
        };

        // Generate VAO and VBO for the crosshair
        crosshairVao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(crosshairVao);

        crosshairVbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, crosshairVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, crosshairVertices, GL15.GL_STATIC_DRAW);

        // Set vertex attribute pointers
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);

        // Unbind VAO
        GL30.glBindVertexArray(0);

        // Load crosshair shaders
        crosshairShaderProgram = loadShaders("/shaders/crosshairVertexShader.glsl", "/shaders/crosshairFragmentShader.glsl");
    }

    private int loadShaders(String vertexShaderPath, String fragmentShaderPath) {
        // Load vertex shader
        String vertexShaderSource = loadShaderSource(vertexShaderPath);
        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexShaderSource);
        GL20.glCompileShader(vertexShader);
        checkShaderCompileStatus(vertexShader, "Vertex Shader");

        // Load fragment shader
        String fragmentShaderSource = loadShaderSource(fragmentShaderPath);
        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentShaderSource);
        GL20.glCompileShader(fragmentShader);
        checkShaderCompileStatus(fragmentShader, "Fragment Shader");

        // Create shader program
        int shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);
        checkProgramLinkStatus(shaderProgram);

        // Delete shaders after linking
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        return shaderProgram;
    }

    private String loadShaderSource(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load shader: " + path, e);
        }
    }

    private void checkShaderCompileStatus(int shader, String shaderType) {
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            String infoLog = GL20.glGetShaderInfoLog(shader);
            throw new RuntimeException(shaderType + " compilation failed: " + infoLog);
        }
    }

    private void checkProgramLinkStatus(int program) {
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            String infoLog = GL20.glGetProgramInfoLog(program);
            throw new RuntimeException("Shader program linking failed: " + infoLog);
        }
    }

    public void run() {
        // Main game loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Clear the screen
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Render the 3D scene
            renderer.render(camera.getViewMatrix(), camera.getProjectionMatrix());

            // Render the crosshair
            renderCrosshair();

            // Swap buffers and poll events
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        // Clean up
        GLFW.glfwTerminate();
    }

    private void renderCrosshair() {
        // Disable depth testing for the crosshair
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // Use the crosshair shader program
        GL20.glUseProgram(crosshairShaderProgram);

        // Bind the crosshair VAO
        GL30.glBindVertexArray(crosshairVao);

        // Draw the crosshair
        GL11.glDrawArrays(GL11.GL_LINES, 0, 4);

        // Unbind the VAO
        GL30.glBindVertexArray(0);

        // Re-enable depth testing
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}