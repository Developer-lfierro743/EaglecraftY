package com.xojangstudios.eaglecrafty.debug;

import org.lwjgl.opengl.GL11;

public class DebugOverlay {

    // FPS counter
    private int fps;
    private long lastFpsTime;

    public DebugOverlay() {
        this.fps = 0;
        this.lastFpsTime = System.currentTimeMillis();
    }

    // Update FPS counter
    public void update() {
        if (System.currentTimeMillis() - lastFpsTime > 1000) {
            lastFpsTime = System.currentTimeMillis();
            fps = 0;
        }
        fps++;
    }

    // Render the debug overlay
    public void render() {
        // Set up 2D rendering
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 600, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // Render FPS text
        renderText("FPS: " + fps, 10, 10);

        // Reset OpenGL state
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    // Simple text rendering (placeholder)
    private void renderText(String text, int x, int y) {
        // In a real implementation, you would use a font renderer or OpenGL text rendering
        System.out.println(text); // Temporary: Print to console
    }
}