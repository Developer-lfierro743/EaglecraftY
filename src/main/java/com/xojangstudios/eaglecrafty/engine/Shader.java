package com.xojangstudios.eaglecrafty.engine;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

public class Shader {
    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    public Shader(String vertexPath, String fragmentPath) {
        // Read and compile the vertex shader
        vertexShaderID = compileShader(vertexPath, GL20.GL_VERTEX_SHADER);
        // Read and compile the fragment shader
        fragmentShaderID = compileShader(fragmentPath, GL20.GL_FRAGMENT_SHADER);
        // Link the shaders into a program
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        GL20.glLinkProgram(programID);
        // Check for linking errors
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error linking shader program: " + GL20.glGetProgramInfoLog(programID));
        }
        // Clean up
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
    }

    public void use() {
        GL20.glUseProgram(programID);
    }

    public void setMat4(String name, Matrix4f mat) {
        int location = GL20.glGetUniformLocation(programID, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            mat.get(fb);
            GL20.glUniformMatrix4fv(location, false, fb);
        }
    }

    private int compileShader(String filePath, int type) {
        int shaderID = GL20.glCreateShader(type);
        String shaderSource = readFile(filePath);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error compiling shader: " + GL20.glGetShaderInfoLog(shaderID));
        }
        return shaderID;
    }

    private String readFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
        return stringBuilder.toString();
    }
}