package com.xojangstudios.eaglecrafty.entities;

import org.joml.Vector3f;

import com.xojangstudios.eaglecrafty.physics.Physics;

public class Player {
    private Vector3f position; // Player's position in the world
    private float pitch; // Rotation around the X-axis (up/down)
    private float yaw;   // Rotation around the Y-axis (left/right)
    private float moveSpeed; // Movement speed
    private float sensitivity; // Mouse sensitivity
                    private final Physics physics;
                
                    public Player(Vector3f position, float moveSpeed, float sensitivity, Physics physics) {
                        this.position = position;
                        this.pitch = 0.0f;
                        this.yaw = 0.0f;
                        this.moveSpeed = moveSpeed;
                        this.sensitivity = sensitivity;
                        this.physics = physics;
                    }
                
                    // Move the player forward
                    public void moveForward(float distance) {
                        Vector3f newPosition = new Vector3f(position);
                        newPosition.x -= distance * (float) Math.sin(Math.toRadians(yaw));
                        newPosition.z += distance * (float) Math.cos(Math.toRadians(yaw));
                        if (physics.canMoveTo(newPosition)) {
                            position = newPosition;
                        }
                    }
                
                    // Move the player backward
                    public void moveBackward(float distance) {
                        Vector3f newPosition = new Vector3f(position);
                        newPosition.x += distance * (float) Math.sin(Math.toRadians(yaw));
                        newPosition.z -= distance * (float) Math.cos(Math.toRadians(yaw));
                        if (physics.canMoveTo(newPosition)) {
                            position = newPosition;
                        }
                    }
                
                    // Move the player left
                    public void moveLeft(float distance) {
                        Vector3f newPosition = new Vector3f(position);
                        newPosition.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
                        newPosition.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
                        if (physics.canMoveTo(newPosition)) {
                            position = newPosition;
                        }
                    }
                
                    // Move the player right
                    public void moveRight(float distance) {
                        Vector3f newPosition = new Vector3f(position);
                        newPosition.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
                        newPosition.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
                        if (physics.canMoveTo(newPosition)) {
                            position = newPosition;
                        }
                    }
                
                    // Move the player up
                    public void moveUp(float distance) {
                        Vector3f newPosition = new Vector3f(position);
                        newPosition.y += distance;
                        if (physics.canMoveTo(newPosition)) {
                            position = newPosition;
                        }
                    }
                
                    // Move the player down
                    public void moveDown(float distance) {
                        Vector3f newPosition = new Vector3f(position);
                        newPosition.y -= distance;
                        if (physics.canMoveTo(newPosition)) {
                            position = newPosition;
                        }
                    }
                
                    // Rotate the player (pitch and yaw)
                    public void rotate(float pitchChange, float yawChange) {
                        this.pitch += pitchChange * sensitivity;
                        this.yaw += yawChange * sensitivity;
                
                        // Clamp pitch to avoid flipping
                        if (this.pitch > 89.0f) {
                            this.pitch = 89.0f;
                        } else if (this.pitch < -89.0f) {
                            this.pitch = -89.0f;
                        }
                    }
                
                    // Get the player's position
                    public Vector3f getPosition() {
                        return position;
                    }
                
                    // Get the player's pitch
                    public float getPitch() {
                        return pitch;
                    }
                
                    // Get the player's yaw
                    public float getYaw() {
                        return yaw;
                    }
                
                    // Set the player's position
                    public void setPosition(Vector3f position) {
                        this.position = position;
                    }
                
                    // Set the player's pitch
                    public void setPitch(float pitch) {
                        this.pitch = pitch;
                    }
                
                    // Set the player's yaw
                    public void setYaw(float yaw) {
                        this.yaw = yaw;
                    }
                
                    // Set the player's movement speed
                    public void setMoveSpeed(float moveSpeed) {
                        this.moveSpeed = moveSpeed;
            }
        
            // Get the player's movement speed
            public float getMoveSpeed() {
                return moveSpeed;
            }
        
            // Set the player's sensitivity
            public void setSensitivity(float sensitivity) {
                this.sensitivity = sensitivity;
    }

    // Get the player's sensitivity
    public float getSensitivity() {
        return sensitivity;
    }
}
