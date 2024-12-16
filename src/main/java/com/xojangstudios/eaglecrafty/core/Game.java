package com.xojangstudios.eaglecrafty.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.xojangstudios.eaglecrafty.entities.Player;
import com.xojangstudios.eaglecrafty.physics.Physics;
import com.xojangstudios.eaglecrafty.rendering.Renderer;
import com.xojangstudios.eaglecrafty.world.World;

public class Game {
    private final long window;
    private final int width = 800;
    private final int height = 600;
    private final String title = "EaglecraftY";

    private Renderer renderer;
    private Player player;
    private World world;
    private Physics physics;

    private double lastMouseX = width / 2.0; // Center of the window
    private double lastMouseY = height / 2.0; // Center of the window
    private boolean firstMouse = true;
    private boolean mouseLocked = true; // Tracks whether the mouse is locked

    private int crosshairVao;
    private int crosshairVbo;
    private int crosshairShaderProgram;

    private float crosshairSize = 0.075f; // Default crosshair size (10% of screen height)
    private final float crosshairThickness = 0.005f; // Thickness of the crosshair lines

    public Game() {
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            System.err.println("Failed to initialize GLFW");
            System.exit(1);
        }

        // Set GLFW window hints
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        // Create a window
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            System.err.println("Failed to create window");
            System.exit(1);
        }

        // Set up OpenGL
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f); // Set a dark background color
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enable depth testing

        // Initialize the Renderer
        renderer = new Renderer();

        // Initialize the World
        world = new World();
        world.generateWorld();

        // Initialize the Physics
        physics = new Physics(world);

        // Initialize the Player
        player = new Player(new Vector3f(0, 5, 5), 0.1f, 0.1f, physics);

        // Set up input handling
        GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
                    float moveSpeed = player.getMoveSpeed();

                    // Handle player movement
                    switch (key) {
                        case GLFW.GLFW_KEY_W -> player.moveForward(moveSpeed);
                        case GLFW.GLFW_KEY_S -> player.moveBackward(moveSpeed);
                        case GLFW.GLFW_KEY_A -> player.moveLeft(moveSpeed);
                        case GLFW.GLFW_KEY_D -> player.moveRight(moveSpeed);
                        case GLFW.GLFW_KEY_SPACE -> player.moveUp(moveSpeed);
                        case GLFW.GLFW_KEY_LEFT_SHIFT -> player.moveDown(moveSpeed);
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

                    // Update player rotation
                    player.rotate((float) deltaY, (float) deltaX);
                }
            }
        });

        // Hide and lock the mouse cursor to the center of the window initially
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        // Initialize the crosshair
        initCrosshair();
    }

    private void initCrosshair() {
        // Create a simple crosshair using two quads
        float[] crosshairVertices = generateCrosshairVertices();

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

    private float[] generateCrosshairVertices() {
        // Dynamically calculate crosshair size based on window size
        IntBuffer widthBuffer = IntBuffer.allocate(1);
        IntBuffer heightBuffer = IntBuffer.allocate(1);
        GLFW.glfwGetWindowSize(window, widthBuffer, heightBuffer);
        int windowWidth = widthBuffer.get(0);
        int windowHeight = heightBuffer.get(0);

        // Calculate crosshair size as 10% of the screen height
        crosshairSize = 0.1f * (float) windowHeight / (float) windowWidth;

        // Create the crosshair vertices
        return new float[]{
            // Horizontal line
            -crosshairSize, -crosshairThickness, 0.0f, // Bottom-left
            crosshairSize, -crosshairThickness, 0.0f,  // Bottom-right
            crosshairSize, crosshairThickness, 0.0f,   // Top-right
            -crosshairSize, crosshairThickness, 0.0f,  // Top-left

            // Vertical line
            -crosshairThickness, -crosshairSize, 0.0f, // Bottom-left
            crosshairThickness, -crosshairSize, 0.0f,  // Bottom-right
            crosshairThickness, crosshairSize, 0.0f,   // Top-right
            -crosshairThickness, crosshairSize, 0.0f   // Top-left
        };
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

            // Update the camera's view matrix based on the player's position and rotation
            Matrix4f viewMatrix = new Matrix4f().identity();
            viewMatrix.rotate((float) Math.toRadians(player.getPitch()), new Vector3f(1, 0, 0)); // Pitch
            viewMatrix.rotate((float) Math.toRadians(player.getYaw()), new Vector3f(0, 1, 0));   // Yaw
            viewMatrix.translate(new Vector3f(-player.getPosition().x, -player.getPosition().y, -player.getPosition().z)); // Position

            // Render the 3D scene
            renderer.render(viewMatrix, new Matrix4f().perspective(70.0f, (float) width / height, 0.1f, 1000.0f));

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
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 12);

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