package com.xojangstudios.eaglecrafty.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class Camera {
    // Camera position
    private final Vector3f position;

    // Camera rotation (pitch and yaw)
    private float pitch; // Rotation around the X-axis (up/down)
    private float yaw;   // Rotation around the Y-axis (left/right)

    // Projection matrix
    private final Matrix4f projectionMatrix;

    // View matrix
    private final Matrix4f viewMatrix;

    // Field of view (in degrees)
    private static final float FOV = 70.0f;

    // Aspect ratio (width / height)
    private static final float ASPECT_RATIO = 800.0f / 600.0f;

    // Near and far clipping planes
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000.0f;

    public Camera(Vector3f position) {
        this.position = position;
        this.pitch = 0.0f;
        this.yaw = 0.0f;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        updateProjectionMatrix();
        updateViewMatrix();
    }

    // Update the projection matrix (perspective projection)
    private void updateProjectionMatrix() {
        projectionMatrix.identity();
        projectionMatrix.perspective(FOV, ASPECT_RATIO, NEAR_PLANE, FAR_PLANE);
    }

    // Update the view matrix based on position and rotation
    private void updateViewMatrix() {
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0)); // Pitch
        viewMatrix.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0));   // Yaw
        viewMatrix.translate(new Vector3f(-position.x, -position.y, -position.z)); // Position
    }

    // Move the camera forward
    public void moveForward(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw));
    }

    // Move the camera backward
    public void moveBackward(float distance) {
        position.x += distance * (float) Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
    }

    // Move the camera left
    public void moveLeft(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }

    // Move the camera right
    public void moveRight(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }

    // Move the camera up
    public void moveUp(float distance) {
        position.y += distance;
    }

    // Move the camera down
    public void moveDown(float distance) {
        position.y -= distance;
    }

    // Rotate the camera (pitch and yaw)
    public void rotate(float pitchChange, float yawChange) {
        this.pitch += pitchChange;
        this.yaw += yawChange;

        // Clamp pitch to avoid flipping
        if (this.pitch > 89.0f) {
            this.pitch = 89.0f;
        } else if (this.pitch < -89.0f) {
            this.pitch = -89.0f;
        }

        updateViewMatrix();
    }

    // Get the view matrix
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    // Get the projection matrix
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    // Get the camera position
    public Vector3f getPosition() {
        return position;
    }
}