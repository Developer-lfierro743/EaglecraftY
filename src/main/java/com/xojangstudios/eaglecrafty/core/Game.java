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
        Window.init();
        // Game loop
        while (!Window.shouldClose()) {
            inputHandler.update();
            world.update();
            renderer.render(world);
            Window.swapBuffers();
            Window.pollEvents();
        }
        Window.terminate();
    }

    public static void main(String[] args) {
        // Create the game instance and start it
        Game game = new Game();
        game.start();
    }
}