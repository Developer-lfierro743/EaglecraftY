package com.xojangstudios.eaglecrafty.engine;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Cube {

    private int vaoId;
    private int vboId;
    private int vertexCount;
    private int shaderProgram;

    public Cube() {
        // Initialization will be done in the init method
    }

    public void init() {
        // Define the cube vertices
        float[] vertices = {
            // Front face
            -0.5f, -0.5f, 0.5f,  0.5f, -0.5f, 0.5f,  0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,  0.5f, 0.5f, 0.5f,  -0.5f, 0.5f, 0.5f,
            // Back face
            -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
            // Left face
            -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f,
            // Right face
            0.5f, -0.5f, -0.5f,  0.5f, -0.5f, 0.5f,  0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, -0.5f,  0.5f, 0.5f, 0.5f,   0.5f, 0.5f, -0.5f,
            // Top face
            -0.5f, 0.5f, -0.5f,  -0.5f, 0.5f, 0.5f,  0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,  0.5f, 0.5f, 0.5f,   0.5f, 0.5f, -0.5f,
            // Bottom face
            -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f,  -0.5f, -0.5f, 0.5f
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

        // Load shaders
        shaderProgram = createShaderProgram(
                loadShader("/path/to/vertex.glsl", GL20.GL_VERTEX_SHADER),
                loadShader("/path/to/fragment.glsl", GL20.GL_FRAGMENT_SHADER)
        );
    }

    public void render(int windowWidth, int windowHeight) {
        // Clear the screen and depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Use the shader program
        GL20.glUseProgram(shaderProgram);

        // Set up the projection matrix
        Matrix4f projectionMatrix = new Matrix4f()
                .perspective((float) Math.toRadians(45), (float) windowWidth / windowHeight, 0.01f, 100.0f);
        int projectionMatrixLocation = GL20.glGetUniformLocation(shaderProgram, "projectionMatrix");
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        projectionMatrix.get(matrixBuffer);
        GL20.glUniformMatrix4fv(projectionMatrixLocation, false, matrixBuffer);

        // Set up the view matrix (camera)
        Matrix4f viewMatrix = new Matrix4f()
                .lookAt(0, 0, 3, 0, 0, 0, 0, 1, 0); // Camera at (0, 0, 3), looking at (0, 0, 0), up vector (0, 1, 0)
        int viewMatrixLocation = GL20.glGetUniformLocation(shaderProgram, "viewMatrix");
        matrixBuffer = BufferUtils.createFloatBuffer(16);
        viewMatrix.get(matrixBuffer);
        GL20.glUniformMatrix4fv(viewMatrixLocation, false, matrixBuffer);

        // Bind the VAO to render the cube
        GL30.glBindVertexArray(vaoId);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount); // Draw the cube's vertices as triangles
        GL30.glBindVertexArray(0); // Unbind the VAO

        // Unuse the shader program
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        GL30.glDeleteBuffers(vboId);
        GL30.glDeleteVertexArrays(vaoId);
        GL20.glDeleteProgram(shaderProgram);
    }

    private int loadShader(String filePath, int type) {
        try {
            String source = new String(Files.readAllBytes(Paths.get(filePath)));
            int shader = GL20.glCreateShader(type);
            GL20.glShaderSource(shader, source);
            GL20.glCompileShader(shader);

            if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                throw new RuntimeException("Shader compilation failed: " + GL20.glGetShaderInfoLog(shader));
            }

            return shader;
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Failed to load shader: " + filePath, e);
        }
    }

    private int createShaderProgram(int vertexShader, int fragmentShader) {
        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vertexShader);
        GL20.glAttachShader(program, fragmentShader);
        GL20.glLinkProgram(program);

        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Shader program linking failed: " + GL20.glGetProgramInfoLog(program));
        }

        GL20.glDetachShader(program, vertexShader);
        GL20.glDetachShader(program, fragmentShader);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        return program;
    }
}