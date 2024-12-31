package com.xojangstudios.eaglecrafty.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.GLFW;
import com.xojangstudios.eaglecrafty.engine.Window;

public class Game {
    private boolean running = false;
    private double lastTime = 0;
    private double deltaTime = 0;

    public void start() {
        init();
        running = true;
        run();
    }

    private void init() {
        Window.init();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    private void run() {
        lastTime = GLFW.glfwGetTime();

        while (running && !Window.shouldClose()) {
            double currentTime = GLFW.glfwGetTime();
            deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            input();
            update(deltaTime);
            render();

            Window.swapBuffers();
            Window.pollEvents();
        }

        cleanup();
    }

    private void input() {
        if (GLFW.glfwGetKey(Window.getWindow(), GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
            running = false;
        }
    }
    
    private void update(double deltaTime) {
        // Update game logic here
        System.out.println("Delta time: " + deltaTime);
    }

    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        // Render game objects here
    }

    private void cleanup() {
        Window.terminate();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}