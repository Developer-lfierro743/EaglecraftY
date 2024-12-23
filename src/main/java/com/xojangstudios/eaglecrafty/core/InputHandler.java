package com.xojangstudios.eaglecrafty.core;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler extends GLFWKeyCallback {
    private Game game;
    private boolean shiftPressed = false;

    public InputHandler(Game game) {
        this.game = game;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true); // Close on ESC
        }

        // Handle player movement
        if (key == GLFW_KEY_W && action != GLFW_RELEASE) {
            game.getPlayer().moveForward();
        }
        if (key == GLFW_KEY_S && action != GLFW_RELEASE) {
            game.getPlayer().moveBackward();
        }
        if (key == GLFW_KEY_A && action != GLFW_RELEASE) {
            game.getPlayer().moveLeft();
        }
        if (key == GLFW_KEY_D && action != GLFW_RELEASE) {
            game.getPlayer().moveRight();
        }

        // Handle other inputs
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