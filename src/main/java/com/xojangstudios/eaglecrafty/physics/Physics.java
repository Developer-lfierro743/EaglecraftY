package com.xojangstudios.eaglecrafty.physics;

import org.joml.Vector3f;

import com.xojangstudios.eaglecrafty.world.World;

public class Physics {
    private final World world;

    public Physics(World world) {
        this.world = world;
    }

    public boolean canMoveTo(Vector3f position) {
        int x = (int) Math.floor(position.x);
        int y = (int) Math.floor(position.y);
        int z = (int) Math.floor(position.z);

        // Check if the block at the new position is solid
        return !world.isBlockSolid(x, y, z);
    }
}
