package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.input;
import com.xojangstudios.eaglecrafty.rendering.Renderer;
import com.xojangstudios.eaglecrafty.world.World;

public class Game {
    private boolean running = false; // Tracks if the game is running
    private Renderer renderer; // Handles rendering
    private World world; // Represents the game world
    private KeyboardInput keyboardInput; // Handles keyboard input

    public void start() {
        init(); // Initialize the game
        gameLoop(); // Start the game loop
    }

    private void init() {
        // Initialize the game window and rendering
        renderer = new Renderer(800, 600, "EaglecraftY: Industrial Revolution");
        renderer.init();

        // Initialize the game world
        world = new World();

        // Initialize keyboard input
        keyboardInput = new KeyboardInput();
        renderer.getWindow().setKeyCallback(keyboardInput);

        running = true; // Mark the game as running
    }

    private void gameLoop() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0; // Target 60 updates per second
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                update(); // Update game logic
                delta--;
            }

            render(); // Render the game
            handleInput(); // Handle player input

            // Check if the window should close
            if (renderer.getWindow().shouldClose()) {
                running = false;
            }
        }

        cleanup(); // Clean up resources
    }

    private void update() {
        // Update game logic (e.g., player movement, world updates)
        world.update();
    }

    private void render() {
        // Render the game world
        renderer.clear();
        world.render(renderer);
        renderer.swapBuffers();
    }

    private void handleInput() {
        // Handle player input (e.g., movement, interactions)
        if (keyboardInput.isKeyPressed(GLFW_KEY_W)) {
            // Move player forward
        }
        if (keyboardInput.isKeyPressed(GLFW_KEY_S)) {
            // Move player backward
        }
        if (keyboardInput.isKeyPressed(GLFW_KEY_A)) {
            // Move player left
        }
        if (keyboardInput.isKeyPressed(GLFW_KEY_D)) {
            // Move player right
        }
    }

    private void cleanup() {
        // Clean up resources (e.g., close the window, release memory)
        renderer.cleanup();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}