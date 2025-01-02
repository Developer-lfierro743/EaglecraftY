package com.xojangstudios.eaglecrafty.engine;

import java.io.IOException;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.xojangstudios.eaglecrafty.world.World;

import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Renderer {
    private static final String SHADER_DIR = "/home/lfierro743/GameProjects/EaglecraftY/src/resources/shaders/";

    private int shaderProgram;
    private int vao;
    private int vbo;

    public void init() {
        // Initialize OpenGL context
        GL.createCapabilities();

        // Load shaders
        shaderProgram = createShaderProgram(
                loadShader(SHADER_DIR + "vertex.glsl", GL20.GL_VERTEX_SHADER),
                loadShader(SHADER_DIR + "fragment.glsl", GL20.GL_FRAGMENT_SHADER)
        );

        // Set up cube data
        float[] vertices = {
            // Front face
            -0.5f, -0.5f,  0.5f,
             0.5f, -0.5f,  0.5f,
             0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,

            // Back face
            -0.5f, -0.5f, -0.5f,
             0.5f, -0.5f, -0.5f,
             0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,

            // Top face
            -0.5f,  0.5f,  0.5f,
             0.5f,  0.5f,  0.5f,
             0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,

            // Bottom face
            -0.5f, -0.5f,  0.5f,
             0.5f, -0.5f,  0.5f,
             0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,

            // Right face
             0.5f, -0.5f,  0.5f,
             0.5f, -0.5f, -0.5f,
             0.5f,  0.5f, -0.5f,
             0.5f,  0.5f,  0.5f,

            // Left face
            -0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f,  0.5f
        };

        // Create VAO and VBO
        vao = GL30.glGenVertexArrays();
        vbo = GL30.glGenBuffers();

        GL30.glBindVertexArray(vao);

        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL30.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL20.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);

        // Clean up
        MemoryUtil.memFree(vertexBuffer);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public void render(World world) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Use the shader program
        GL20.glUseProgram(shaderProgram);

        // Bind the VAO
        GL30.glBindVertexArray(vao);

        // Draw the cube
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 24);

        // Unbind the VAO
        GL30.glBindVertexArray(0);

        // Unuse the shader program
        GL20.glUseProgram(0);
    }

    public void cleanup() {
        // Delete VAO and VBO
        GL30.glDeleteVertexArrays(vao);
        GL30.glDeleteBuffers(vbo);

        // Delete shader program
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