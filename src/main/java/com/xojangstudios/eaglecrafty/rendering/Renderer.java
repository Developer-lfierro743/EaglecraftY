package com.xojangstudios.eaglecrafty.rendering;

import org.lwjgl.opengl.GL11;
import com.xojangstudios.eaglecrafty.world.World;

public class Renderer {
    private final World world;

    public Renderer() {
        world = new World();
        initOpenGL();
    }

    private void initOpenGL() {
        // Enable depth testing
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Set the clear color (background color)
        GL11.glClearColor(0.5f, 0.8f, 1.0f, 1.0f);

        // Set up the viewport
        GL11.glViewport(0, 0, 800, 600); // Adjust to your window size
    }

    public void render() {
        // Clear the color and depth buffers
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Render the world
        for (int x = 0; x < World.WORLD_WIDTH; x++) {
            for (int z = 0; z < World.WORLD_DEPTH; z++) {
                if (world.getBlock(x, 0, z) == 1) {
                    renderBlock(x, 0, z);
                }
            }
        }
    }

    private void renderBlock(int x, int y, int z) {
        // Render all six faces of the block
        renderFace(x, y, z, 0, 0, 1, 0, 1, 1); // Front face
        renderFace(x, y, z, 1, 0, 0, 1, 1, 0); // Back face
        renderFace(x, y, z, 0, 1, 0, 0, 1, 1); // Left face
        renderFace(x, y, z, 1, 0, 1, 1, 1, 0); // Right face
        renderFace(x, y, z, 0, 0, 0, 1, 0, 1); // Top face
        renderFace(x, y, z, 0, 1, 1, 0, 0, 0); // Bottom face
    }

    private void renderFace(int x, int y, int z, int a, int b, int c, int d, int e, int f) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(x + a, y + b, z + c);
        GL11.glVertex3f(x + d, y + e, z + f);
        GL11.glVertex3f(x + d, y + e + 1, z + f);
        GL11.glVertex3f(x + a, y + b + 1, z + c);
        GL11.glEnd();
    }
}