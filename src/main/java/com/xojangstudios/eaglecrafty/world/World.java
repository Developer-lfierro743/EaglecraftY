package com.xojangstudios.eaglecrafty.world;

public class World {
    private static final int WORLD_WIDTH = 16;
    private static final int WORLD_HEIGHT = 16;
    private static final int WORLD_DEPTH = 16;

    private final int[][][] blocks;

    public World() {
        blocks = new int[WORLD_WIDTH][WORLD_HEIGHT][WORLD_DEPTH];
    }

    public void generateWorld() {
        System.out.println("Generating world...");
        // Simple flat world generation
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int z = 0; z < WORLD_DEPTH; z++) {
                blocks[x][0][z] = 1; // Place a block at y = 0
            }
        }
    }

    public int getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }
}