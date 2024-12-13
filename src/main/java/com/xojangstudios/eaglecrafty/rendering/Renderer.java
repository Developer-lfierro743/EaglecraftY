package com.xojangstudios.eaglecrafty.rendering;

import com.xojangstudios.eaglecrafty.world.World;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Renderer {
    private final World world;
    private int vaoId;
    private int vboId;
    private int eboId;
    private int shaderProgram;

    public Renderer() {
        world = new World();
        initOpenGL();
        loadShaders();
    }

    private void initOpenGL() {
        // Enable depth testing
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Set the clear color (background color)
        GL11.glClearColor(0.5f, 0.8f, 1.0f, 1.0f);

        // Set up the viewport
        GL11.glViewport(0, 0, 800, 600); // Adjust to your window size

        // Initialize Vertex Array Object (VAO)
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Initialize Vertex Buffer Object (VBO)
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        // Initialize Element Buffer Object (EBO)
        eboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eboId);

        // Unbind VAO
        GL30.glBindVertexArray(0);

        // Check for OpenGL errors
        checkGLError("initOpenGL");
    }

    private void loadShaders() {
        // Load vertex shader
        String vertexShaderSource = loadShaderSource("/shaders/vertexShader.glsl");
        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexShaderSource);
        GL20.glCompileShader(vertexShader);
        checkShaderCompileStatus(vertexShader, "Vertex Shader");

        // Load fragment shader
        String fragmentShaderSource = loadShaderSource("/shaders/fragmentShader.glsl");
        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentShaderSource);
        GL20.glCompileShader(fragmentShader);
        checkShaderCompileStatus(fragmentShader, "Fragment Shader");

        // Create shader program
        shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);
        checkProgramLinkStatus(shaderProgram);

        // Delete shaders after linking
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    private String loadShaderSource(String path) {
        try (var inputStream = getClass().getResourceAsStream(path);
             var reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8))) {
            return reader.lines().collect(java.util.stream.Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load shader: " + path, e);
        }
    }

    private void checkShaderCompileStatus(int shader, String shaderType) {
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            String infoLog = GL20.glGetShaderInfoLog(shader);
            throw new RuntimeException(shaderType + " compilation failed: " + infoLog);
        }
    }

    private void checkProgramLinkStatus(int program) {
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            String infoLog = GL20.glGetProgramInfoLog(program);
            throw new RuntimeException("Shader program linking failed: " + infoLog);
        }
    }

    public void render(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        // Clear the color and depth buffers
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Use the shader program
        GL20.glUseProgram(shaderProgram);

        // Bind the VAO
        GL30.glBindVertexArray(vaoId);

        // Enable vertex attribute arrays
        GL20.glEnableVertexAttribArray(0); // Vertex positions
        GL20.glEnableVertexAttribArray(1); // Texture coordinates

        // Render the world
        for (int x = 0; x < World.WORLD_WIDTH; x++) {
            for (int z = 0; z < World.WORLD_DEPTH; z++) {
                if (world.getBlock(x, 0, z) == 1) {
                    renderBlock(x, 0, z, viewMatrix, projectionMatrix);
                }
            }
        }

        // Disable vertex attribute arrays
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        // Unbind the VAO
        GL30.glBindVertexArray(0);

        // Unbind the shader program
        GL20.glUseProgram(0);

        // Check for OpenGL errors
        checkGLError("render");
    }

    private void renderBlock(int x, int y, int z, Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        float[] vertices = {
            // Front face
            x, y, z + 1, 0, 0,
            x + 1, y, z + 1, 1, 0,
            x + 1, y + 1, z + 1, 1, 1,
            x, y + 1, z + 1, 0, 1,
            // Back face
            x, y, z, 0, 0,
            x + 1, y, z, 1, 0,
            x + 1, y + 1, z, 1, 1,
            x, y + 1, z, 0, 1,
            // Left face
            x, y, z, 0, 0,
            x, y, z + 1, 1, 0,
            x, y + 1, z + 1, 1, 1,
            x, y + 1, z, 0, 1,
            // Right face
            x + 1, y, z, 0, 0,
            x + 1, y, z + 1, 1, 0,
            x + 1, y + 1, z + 1, 1, 1,
            x + 1, y + 1, z, 0, 1,
            // Top face
            x, y + 1, z, 0, 0,
            x + 1, y + 1, z, 1, 0,
            x + 1, y + 1, z + 1, 1, 1,
            x, y + 1, z + 1, 0, 1,
            // Bottom face
            x, y, z, 0, 0,
            x + 1, y, z, 1, 0,
            x + 1, y, z + 1, 1, 1,
            x, y, z + 1, 0, 1
        };

        int[] indices = {
            0, 1, 2, 2, 3, 0, // Front face
            4, 5, 6, 6, 7, 4, // Back face
            8, 9, 10, 10, 11, 8, // Left face
            12, 13, 14, 14, 15, 12, // Right face
            16, 17, 18, 18, 19, 16, // Top face
            20, 21, 22, 22, 23, 20 // Bottom face
        };

        // Upload vertex data to VBO
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

        // Upload index data to EBO
        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
        indexBuffer.put(indices).flip();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eboId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);

        // Set vertex attribute pointers
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 5 * 4, 0); // Vertex positions
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 5 * 4, 3 * 4); // Texture coordinates

        // Pass matrices to the shader
        Matrix4f modelMatrix = new Matrix4f().translate(x, y, z);
        setShaderUniforms(modelMatrix, viewMatrix, projectionMatrix);

        // Draw the block
        GL11.glDrawElements(GL11.GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_INT, 0);

        // Check for OpenGL errors
        checkGLError("renderBlock");
    }

    private void setShaderUniforms(Matrix4f modelMatrix, Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        int modelLocation = GL20.glGetUniformLocation(shaderProgram, "model");
        int viewLocation = GL20.glGetUniformLocation(shaderProgram, "view");
        int projectionLocation = GL20.glGetUniformLocation(shaderProgram, "projection");

        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        modelMatrix.get(matrixBuffer);
        GL20.glUniformMatrix4fv(modelLocation, false, matrixBuffer);

        viewMatrix.get(matrixBuffer);
        GL20.glUniformMatrix4fv(viewLocation, false, matrixBuffer);

        projectionMatrix.get(matrixBuffer);
        GL20.glUniformMatrix4fv(projectionLocation, false, matrixBuffer);
    }

    private void checkGLError(String location) {
        int error;
        while ((error = GL11.glGetError()) != GL11.GL_NO_ERROR) {
            System.err.println("OpenGL error at " + location + ": " + error);
        }
    }
}