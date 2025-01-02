package com.xojangstudios.eaglecrafty.input;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import org.lwjgl.glfw.GLFW;

import com.xojangstudios.eaglecrafty.entities.Player;

public class InputHandler {
    private final Player player; // Reference to the player object
    private boolean isMouseLocked; // Track the mouse lock state
    private boolean isEscapePressed; // Track the ESC key press state

    public InputHandler(Player player) {
        this.player = player;
        this.isMouseLocked = false; // Initialize the mouse lock state to false
        this.isEscapePressed = false; // Initialize the ESC key press state to false
    }

    public void update() {
        // Get the current window context
        long window = glfwGetCurrentContext();

        // Check for mouse lock key press (e.g., E)
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_E) == GLFW.GLFW_PRESS) {
            toggleMouseLock();
        }

        // Check for ESC key press
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
            isEscapePressed = true;
        } else {
            isEscapePressed = false;
        }

        // Check for WASD key presses and move the player
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
            player.move(0, 0, -0.1f); // Move forward
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
            player.move(0, 0, 0.1f); // Move backward
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
            player.move(-0.1f, 0, 0); // Move left
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
            player.move(0.1f, 0, 0); // Move right
        }
    }

    private void toggleMouseLock() {
        isMouseLocked = !isMouseLocked;

        if (isMouseLocked) {
            glfwSetInputMode(glfwGetCurrentContext(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        } else {
            glfwSetInputMode(glfwGetCurrentContext(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
    }

    public boolean isEscapePressed() {
        return isEscapePressed;
    }
}
