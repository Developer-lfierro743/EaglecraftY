package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Cube {

    private final int vaoId;
    private final int vboId;
    private final int vertexCount;

    public Cube() {
        // Define the cube vertices
        float[] vertices = {
            // Front face
            -0.5f, -0.5f, 0.5f,  
             0.5f, -0.5f, 0.5f,  
             0.5f, 0.5f, 0.5f,  
            -0.5f, 0.5f, 0.5f,  
            
            // Back face
            -0.5f, -0.5f, -0.5f,  
            -0.5f, 0.5f, -0.5f,  
             0.5f, 0.5f, -0.5f,  
             0.5f, -0.5f, -0.5f,  
            
            // Left face
            -0.5f, -0.5f, -0.5f,  
            -0.5f, -0.5f, 0.5f,  
            -0.5f, 0.5f, 0.5f,  
            -0.5f, 0.5f, -0.5f,  
            
            // Right face
             0.5f, -0.5f, -0.5f,  
             0.5f, -0.5f, 0.5f,  
             0.5f, 0.5f, 0.5f,  
             0.5f, 0.5f, -0.5f,  
            
            // Top face
            -0.5f, 0.5f, -0.5f,  
            -0.5f, 0.5f, 0.5f,  
             0.5f, 0.5f, 0.5f,  
             0.5f, 0.5f, -0.5f,  
            
            // Bottom face
            -0.5f, -0.5f, -0.5f,  
             0.5f, -0.5f, -0.5f,  
             0.5f, -0.5f, 0.5f,  
            -0.5f, -0.5f, 0.5f  
        };

        vertexCount = vertices.length / 3;

        // Create a Vertex Array Object (VAO) and Vertex Buffer Object (VBO)
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        vboId = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);

        // Enable the position attribute (this is how we'll access the vertex positions)
        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
        GL30.glEnableVertexAttribArray(0);

        GL30.glBindVertexArray(0); // Unbind the VAO
    }

    public void render() {
        // Bind the VAO to render the cube
        GL30.glBindVertexArray(vaoId);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, vertexCount); // Draw the cube's vertices as quads
        GL30.glBindVertexArray(0); // Unbind the VAO
    }

    public void cleanUp() {
        GL30.glDeleteBuffers(vboId);
        GL30.glDeleteVertexArrays(vaoId);
    }
}