package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Shader {
    private final int programId;

    public Shader(String vertexPath, String fragmentPath) {
        int vertexShader = compileShader(vertexPath, GL20.GL_VERTEX_SHADER);
        int fragmentShader = compileShader(fragmentPath, GL20.GL_FRAGMENT_SHADER);

        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShader);
        GL20.glAttachShader(programId, fragmentShader);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Failed to link shader program: " + GL20.glGetProgramInfoLog(programId));
        }

        GL20.glDetachShader(programId, vertexShader);
        GL20.glDetachShader(programId, fragmentShader);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    private int compileShader(String path, int type) {
        String source = loadShaderSource(path);
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Failed to compile shader: " + GL20.glGetShaderInfoLog(shaderId));
        }

        return shaderId;
    }

    private String loadShaderSource(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load shader source from " + path, e);
        }
    }

    public void use() {
        GL20.glUseProgram(programId);
    }

    public void cleanup() {
        GL20.glDeleteProgram(programId);
    }
}