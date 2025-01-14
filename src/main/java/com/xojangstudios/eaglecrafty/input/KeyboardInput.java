package com.xojangstudios.eaglecrafty.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput extends GLFWKeyCallback {
    private boolean[] keys = new boolean[GLFW_KEY_LAST + 1];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key >= 0 && key < keys.length) {
            keys[key] = action != GLFW_RELEASE;
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return keyCode >= 0 && keyCode < keys.length && keys[keyCode];
    }
}