package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.input.InputHandler;
import com.xojangstudios.eaglecrafty.engine.Renderer;
import com.xojangstudios.eaglecrafty.engine.Window;
import com.xojangstudios.eaglecrafty.world.World;
import com.xojangstudios.eaglecrafty.assets.AssetManager;

public class Game{
    private Renderer renderer;
    private InputHandler inputHandler;
    private World world;
    @SuppressWarnings("unused")
    private AssetManager assetManager;

    public Game(){
        //initialize the components
        renderer = new Renderer();
        inputHandler = new InputHandler();
        world = new World();
        assetsManager = new AssetManager();
    }

    public void start() {
        //game loop
        while(!Window.ShouldClose()){
            inputHandler.update();
            world.update();
            renderer.render(world);
            Window.swapBuffers();
        }
    }
public static void main(String[] args) {
    // Create the game Instance and start it
    Game game = new Game();
    game.start();
 }
}
