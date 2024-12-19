package com.xojangstudios.eaglecrafty.entities;

import org.joml.Vector3f;
import com.xojangstudios.eaglecrafty.core.*;

public class Player {

    // Player position
    private Vector3f position;

    // Player rotation (pitch and yaw)
    private float pitch; // Up/down rotation
    private float yaw;   // Left/right rotation

    // Movement speed
    private float moveSpeed = 0.1f;

    // Camera reference
    private Camera camera;

    public Player(Camera camera) {
        this.position = new Vector3f(0, 0, 0);
        this.pitch = 0;
        this.yaw = 0;
        this.camera = camera;
    }

    // Update the player's position and rotation
    public void update() {
        // Update the camera's position and rotation
        camera.setPosition(position);
        camera.setRotation(pitch, yaw);
        camera.updateViewMatrix();
    }

    // Move the player forward
    public void moveForward() {
        position.x += moveSpeed * Math.sin(Math.toRadians(yaw));
        position.z -= moveSpeed * Math.cos(Math.toRadians(yaw));
    }

    // Move the player backward
    public void moveBackward() {
        position.x -= moveSpeed * Math.sin(Math.toRadians(yaw));
        position.z += moveSpeed * Math.cos(Math.toRadians(yaw));
    }

    // Move the player left
    public void moveLeft() {
        position.x -= moveSpeed * Math.sin(Math.toRadians(yaw - 90));
        position.z += moveSpeed * Math.cos(Math.toRadians(yaw - 90));
    }

    // Move the player right
    public void moveRight() {
        position.x -= moveSpeed * Math.sin(Math.toRadians(yaw + 90));
        position.z += moveSpeed * Math.cos(Math.toRadians(yaw + 90));
    }

    // Rotate the player (and camera)
    public void rotate(float deltaPitch, float deltaYaw) {
        pitch += deltaPitch;
        yaw += deltaYaw;

        // Clamp pitch to avoid flipping
        pitch = Math.max(-90, Math.min(90, pitch));
    }

    // Get the player's position
    public Vector3f getPosition() {
        return position;
    }

    // Get the player's rotation
    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}