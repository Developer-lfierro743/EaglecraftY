package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.entities.Player;
import com.xojangstudios.eaglecrafty.input.InputHandler;
import com.xojangstudios.eaglecrafty.engine.Renderer;
import com.xojangstudios.eaglecrafty.engine.Window;
import com.xojangstudios.eaglecrafty.world.World;
import com.xojangstudios.eaglecrafty.assets.AssetManager;

public class Game {
    private final Renderer renderer;
    private final InputHandler inputHandler;
    private final World world;
    private final Player player; // Add a Player object
    @SuppressWarnings("unused")
    private final AssetManager assetManager;

    public Game() {
        // Initialize the components
        renderer = new Renderer();
        player = new Player(); // Initialize the player
        inputHandler = new InputHandler(player); // Pass the player to the input handler
        world = new World();
        assetManager = new AssetManager();

        // Log build and version information
        if (SharedConstants.ENABLE_LOGGING) {
            System.out.println("Starting EaglecraftY - Version: " + SharedConstants.VERSION + " (Build: " + SharedConstants.VERSION_BUILD + ")");
            System.out.println("Build Type: " + SharedConstants.BUILD_TYPE);
        }
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

            // Update the player
            player.update();

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

        // Log shutdown messagea
        if (SharedConstants.ENABLE_LOGGING) {
            System.out.println("EaglecraftY has been shut down.");
        }
    }

    public static void main(String[] args) {
        // Create the game instance and start it
        Game game = new Game();
        game.start();
    }
}