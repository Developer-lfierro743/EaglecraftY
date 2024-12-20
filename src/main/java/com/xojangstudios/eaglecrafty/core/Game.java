package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.entities.Player;
import com.xojangstudios.eaglecrafty.world.World;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Game {
    private long window;
    private Renderer renderer;
    private Player player;
    private Camera camera;
    private InputHandler inputHandler;
    private boolean running;

    public Game() {
        this.renderer = new Renderer();
        this.camera = new Camera(70.0f, 800.0f / 600.0f, 0.01f, 1000.0f);
        this.player = new Player(camera);
        this.inputHandler = new InputHandler(this);
    }

    public void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        window = GLFW.glfwCreateWindow(800, 600, "EaglecraftY", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();

        renderer.init();
        GLFW.glfwSetKeyCallback(window, inputHandler);
    }

    public void run() {
        running = true;
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            update();
            renderer.render();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        cleanup();
    }

    private void update() {
        player.update();
        // Additional game logic updates
    }

    private void cleanup() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }

    public Player getPlayer() {
        return player;
    }

    public void setInputHandler(InputHandler inputHandler2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInputHandler'");
    }

    public void setRenderer(Renderer renderer2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRenderer'");
    }

    public void setWorld(World world) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setWorld'");
    }

    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}
