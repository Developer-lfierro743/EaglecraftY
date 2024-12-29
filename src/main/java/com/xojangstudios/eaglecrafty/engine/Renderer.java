package com.xojangstudios.eaglecrafty.engine;

import com.xojangstudios.eaglecrafty.world.World;

public class Renderer {
    public Renderer(){
        // Initialize renderering context (opengl,shaders,etc)
        initOpenGL();
    }

    private void initOpenGL(){
        // Setup Opengl,shaders,buffers,etc
        System.out.println("OpenGL Initialized");
    }

    public void render(World world){
        // Render the world
        System.out.println("Rendering World");
    }
    
}
