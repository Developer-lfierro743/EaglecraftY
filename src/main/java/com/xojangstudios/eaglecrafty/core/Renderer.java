package com.xojangstudios.eaglecrafty.core;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Renderer {
    public void init() {
        GL.createCapabilities();
        GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        // Add your rendering logic here
    }
}