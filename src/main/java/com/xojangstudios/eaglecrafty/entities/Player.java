package com.xojangstudios.eaglecrafty.entities;

import org.joml.Vector3f;

import com.xojangstudios.eaglecrafty.core.SharedConstants;

public class Player {
    private Vector3f position; // Player's position in 3D space

    public Player() {
        this.position = new Vector3f(0, 0, 0); // Start at the origin
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    // Move the player in a specific direction
    public void move(float offsetX, float offsetY, float offsetZ) {
        position.x += offsetX;
        position.y += offsetY;
        position.z += offsetZ;

        // Debug: Print the player's new position
        if (SharedConstants.ENABLE_LOGGING) {
            System.out.println("Player moved to: (" + position.x + ", " + position.y + ", " + position.z + ")");
        }
    }

    // Update the player's state (e.g., handle physics, collisions, etc.)
    public void update() {
        // Add update logic here if needed
    }
}