package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.opengl.GL30;

public class Cube {
    private final int vaoId;

    public Cube() {
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        // Define cube geometry and upload to GPU
        // Add buffer setup here (VBOs, EBOs, etc.)
        GL30.glBindVertexArray(0);
    }

    public void render() {
        GL30.glBindVertexArray(vaoId);
        // Render cube geometry (e.g., GL11.glDrawArrays)
        GL30.glBindVertexArray(0);
    }

    public void cleanUp() {
        GL30.glDeleteVertexArrays(vaoId);
    }
}