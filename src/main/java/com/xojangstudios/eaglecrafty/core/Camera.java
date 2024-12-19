package com.xojangstudios.eaglecrafty.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    // Camera position
    private Vector3f position;

    // Camera rotation (pitch and yaw)
    private float pitch; // Up/down rotation
    private float yaw;   // Left/right rotation

    // Projection matrix
    private Matrix4f projectionMatrix;

    // View matrix
    private Matrix4f viewMatrix;

    public Camera(float fov, float aspectRatio, float nearPlane, float farPlane) {
        this.position = new Vector3f(0, 0, 0);
        this.pitch = 0;
        this.yaw = 0;

        // Initialize projection matrix
        this.projectionMatrix = new Matrix4f().perspective(fov, aspectRatio, nearPlane, farPlane);

        // Initialize view matrix
        this.viewMatrix = new Matrix4f();
    }

    // Update the view matrix based on the camera's position and rotation
    public void updateViewMatrix() {
        viewMatrix.identity()
                .rotateX(pitch)
                .rotateY(yaw)
                .translate(-position.x, -position.y, -position.z);
    }

    // Move the camera to a new position
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    // Rotate the camera
    public void setRotation(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }

    // Get the projection matrix
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    // Get the view matrix
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
}