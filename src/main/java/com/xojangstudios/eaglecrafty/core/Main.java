package com.xojangstudios.eaglecrafty.core;
import com.xojangstudios.eaglecrafty.world.World;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to EaglecraftY Java Edition!");

        // Initialize game components
        Game game = new Game();
        InputHandler inputHandler = new InputHandler(game);
        Renderer renderer = new Renderer();
        World world = new World(game);

        // Connect components
        game.setInputHandler(inputHandler);
        game.setRenderer(renderer);
        game.setWorld(world);

        // Start the game loop
        game.start();
    }
}