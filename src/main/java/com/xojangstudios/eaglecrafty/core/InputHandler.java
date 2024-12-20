package com.xojangstudios.eaglecrafty.core;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler extends GLFWKeyCallback {
    private boolean shiftPressed = false;

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true); // Close on ESC
        }

        if (key == GLFW_KEY_LEFT_SHIFT || key == GLFW_KEY_RIGHT_SHIFT) {
            shiftPressed = action != GLFW_RELEASE;
        }

        if (shiftPressed && key == GLFW_KEY_Z && action == GLFW_PRESS) {
            unlockSecretDebugMenu();
        }
    }

    private void unlockSecretDebugMenu() {
        // Implement the logic to unlock the secret debugging menu
        System.out.println("Secret Debugging Menu Unlocked!");
    }
}