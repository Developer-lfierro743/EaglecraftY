package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.input.InputHandler;
import com.xojangstudios.eaglecrafty.engine.Renderer;
import com.xojangstudios.eaglecrafty.engine.Window;
import com.xojangstudios.eaglecrafty.world.World;
import com.xojangstudios.eaglecrafty.assets.AssetManager;

public class Game {
    private final Renderer renderer;
    private final InputHandler inputHandler;
    private final World world;
    @SuppressWarnings("unused")
    private final AssetManager assetManager;

    public Game() {
        // Initialize the components
        renderer = new Renderer();
        inputHandler = new InputHandler();
        world = new World();
        assetManager = new AssetManager();
    }

    public void start() {
        // Initialize the window and OpenGL context
        Window.init(800, 600, "EaglecraftY");

        // Initialize the renderer (after OpenGL context is created)
        renderer.init();

        // Game loop
        while (!Window.shouldClose()) {
            // Handle input
            inputHandler.update();

            // Update the world
            world.update();

            // Render the world
            renderer.render(world, Window.getWidth(), Window.getHeight());

            // Swap buffers and poll events
            Window.swapBuffers();
            Window.pollEvents();
        }

        // Clean up resources
        renderer.cleanup();
        Window.terminate();
    }

    public static void main(String[] args) {
        // Create the game instance and start it
        Game game = new Game();
        game.start();
    }
}