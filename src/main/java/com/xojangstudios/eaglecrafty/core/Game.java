package com.xojangstudios.eaglecrafty.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

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

    private boolean isTouchDevice = false; // Flag to detect if running on a touch device

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

        // Detect if running on a touch device (e.g., Termux/Proot-Distro with Termux-X11)
        isTouchDevice = detectTouchDevice();

        // Set up input handling
        if (isTouchDevice) {
            setupTouchControls();
        } else {
            setupKeyboardMouseControls();
        }
    }

    private boolean detectTouchDevice() {
        // Placeholder for touch device detection logic
        // You can use environment variables or other methods to detect Termux/Proot-Distro
        return System.getenv("TERMUX_X11") != null;
    }

    private void setupTouchControls() {
        // Set up touch controls (e.g., virtual joystick, swipe gestures)
        System.out.println("Touch controls enabled");

        // Example: Map touch events to player actions
        GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double mouseX, double mouseY) {
                // Handle touch events (e.g., swipe gestures for rotation)
                if (firstMouse) {
                    lastMouseX = mouseX;
                    lastMouseY = mouseY;
                    firstMouse = false;
                }

                // Calculate touch movement delta
                double deltaX = mouseX - lastMouseX;
                double deltaY = lastMouseY - mouseY; // Reversed since y-coordinates go from bottom to top

                // Update last touch position
                lastMouseX = mouseX;
                lastMouseY = mouseY;

                // Update player rotation based on touch movement
                player.rotate((float) deltaY, (float) deltaX);
            }
        });
    }

    private void setupKeyboardMouseControls() {
        // Set up keyboard and mouse controls
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