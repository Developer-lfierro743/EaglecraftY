package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.engine.Renderer;
import com.xojangstudios.eaglecrafty.engine.Window;

public class Game {
    private Renderer renderer;

    public void start() {
        Window.init();
        renderer = new Renderer();
        renderer.init();

        while (!Window.shouldClose()) {
            renderer.render();
            Window.swapBuffers();
            Window.pollEvents();
        }

        renderer.cleanup();
        Window.terminate();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}