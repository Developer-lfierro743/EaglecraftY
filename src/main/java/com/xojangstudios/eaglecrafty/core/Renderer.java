package com.xojangstudios.eaglecrafty.core;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import java.util.HashMap;
import java.util.Map;

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
public class Renderer {
    private Map<String, Block> blocks;

    public Renderer() {
        blocks = new HashMap<>();
        initializeBlocks();
    }

    private void initializeBlocks() {
        // Basic Minecraft blocks
        blocks.put("stone", new Block("stone", 1.5f, 10.0f));
        blocks.put("dirt", new Block("dirt", 0.5f, 2.5f));
        blocks.put("grass", new Block("grass", 0.6f, 3.0f));
        blocks.put("sand", new Block("sand", 0.5f, 2.5f));
        blocks.put("wood", new Block("wood", 2.0f, 15.0f));

        // Custom blocks
        blocks.put("steel_ore", new Block("steel_ore", 3.0f, 20.0f));
        blocks.put("ruby_block", new Block("ruby_block", 5.0f, 30.0f));
        blocks.put("sapphire_block", new Block("sapphire_block", 5.0f, 30.0f));
        blocks.put("emerald_block", new Block("emerald_block", 5.0f, 30.0f));
    }

    public Block getBlock(String name) {
        return blocks.get(name);
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        // Add your rendering logic here
    }

    public static class Block {
        private String name;
        private float hardness;
        private float resistance;

        public Block(String name, float hardness, float resistance) {
            this.name = name;
            this.hardness = hardness;
            this.resistance = resistance;
        }

        public String getName() {
            return name;
        }

        public float getHardness() {
            return hardness;
        }

        public float getResistance() {
            return resistance;
        }
    }
}