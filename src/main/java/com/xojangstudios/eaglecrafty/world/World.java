package com.xojangstudios.eaglecrafty.world;

<<<<<<< HEAD
public class World {

    public World() {
        // Initialize the world
        System.out.println("World initialized");
    }

    public void update() {
        // Add logic to update the world state
        System.out.println("World updated!");
=======
import com.xojangstudios.eaglecrafty.rendering.Renderer;

public class World {
    public void update() {
        // Update world logic (e.g., mobs, time of day)
    }

    public void render(Renderer renderer) {
        // Render the world (e.g., blocks, entities)
>>>>>>> 1de10c0 (redoing everything from scratch(final updaate))
    }
}