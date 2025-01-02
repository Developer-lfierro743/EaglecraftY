package com.xojangstudios.eaglecrafty.core;

import com.xojangstudios.eaglecrafty.entities.Player;
import com.xojangstudios.eaglecrafty.input.InputHandler;
import com.xojangstudios.eaglecrafty.engine.Renderer;
import com.xojangstudios.eaglecrafty.engine.Window;
import com.xojangstudios.eaglecrafty.world.World;
import com.xojangstudios.eaglecrafty.ui.UIManager;
import com.xojangstudios.eaglecrafty.ui.Button;
import com.xojangstudios.eaglecrafty.ui.Label;
import com.xojangstudios.eaglecrafty.ui.Panel;
import com.xojangstudios.eaglecrafty.assets.AssetManager;
import org.joml.Vector2f;

public class Game {
    private final Renderer renderer;
    private final UIManager uiManager;
    private final InputHandler inputHandler;
    private final World world;
    private final Player player; // Add a Player object
    @SuppressWarnings("unused")
    private final AssetManager assetManager;
    private boolean isPaused;

    public Game() {
        // Initialize the components
        renderer = new Renderer();
        uiManager = new UIManager();
        player = new Player(); // Initialize the player
        inputHandler = new InputHandler(player); // Pass the player to the input handler
        world = new World();
        assetManager = new AssetManager();
        isPaused = false;

        // Log build and version information
        if (SharedConstants.ENABLE_LOGGING) {
            System.out.println("Starting EaglecraftY - Version: " + SharedConstants.VERSION + " (Build: " + SharedConstants.VERSION_BUILD + ")");
            System.out.println("Build Type: " + SharedConstants.BUILD_TYPE);
        }

        // Initialize the pause menu
        initializePauseMenu();
    }

    private void initializePauseMenu() {
        Panel pauseMenu = new Panel(new Vector2f(200, 100), new Vector2f(400, 300), "PauseMenu");

        Label title = new Label(new Vector2f(0, 0), new Vector2f(400, 50), "Pause Menu");
        pauseMenu.addChild(title);

        float buttonY = 60;
        float buttonHeight = 40;
        float buttonSpacing = 10;

        Button resumeButton = new Button(new Vector2f(50, buttonY), new Vector2f(300, buttonHeight), "Back to Game", () -> isPaused = false);
        pauseMenu.addChild(resumeButton);

        buttonY += buttonHeight + buttonSpacing;
        Button advancementsButton = new Button(new Vector2f(50, buttonY), new Vector2f(300, buttonHeight), "Advancements", this::showAdvancements);
        pauseMenu.addChild(advancementsButton);

        buttonY += buttonHeight + buttonSpacing;
        Button statisticsButton = new Button(new Vector2f(50, buttonY), new Vector2f(300, buttonHeight), "Statistics", this::showStatistics);
        pauseMenu.addChild(statisticsButton);

        buttonY += buttonHeight + buttonSpacing;
        Button feedbackButton = new Button(new Vector2f(50, buttonY), new Vector2f(300, buttonHeight), "Give Feedback", this::giveFeedback);
        pauseMenu.addChild(feedbackButton);

        buttonY += buttonHeight + buttonSpacing;
        Button reportBugsButton = new Button(new Vector2f(50, buttonY), new Vector2f(300, buttonHeight), "Report Bugs", this::reportBugs);
        pauseMenu.addChild(reportBugsButton);

        buttonY += buttonHeight + buttonSpacing;
        Button optionsButton = new Button(new Vector2f(50, buttonY), new Vector2f(300, buttonHeight), "Options", this::showOptions);
        pauseMenu.addChild(optionsButton);

        buttonY += buttonHeight + buttonSpacing;
        Button playerReportingButton = new Button(new Vector2f(50, buttonY), new Vector2f(300, buttonHeight), "Player Reporting", this::playerReporting);
        pauseMenu.addChild(playerReportingButton);

        buttonY += buttonHeight + buttonSpacing;
        Button disconnectButton = new Button(new Vector2f(50, buttonY), new Vector2f(300, buttonHeight), "Disconnect", this::disconnect);
        pauseMenu.addChild(disconnectButton);

        uiManager.addElement(pauseMenu);
        pauseMenu.setVisible(false); // Initially hide the pause menu
    }

    private void showAdvancements() {
        // Implement logic to show advancements
    }

    private void showStatistics() {
        // Implement logic to show statistics
    }

    private void giveFeedback() {
        // Implement logic to give feedback
    }

    private void reportBugs() {
        // Implement logic to report bugs
    }

    private void showOptions() {
        // Implement logic to show options
    }

    private void playerReporting() {
        // Implement logic to handle player reporting
    }

    private void disconnect() {
        // Implement logic to disconnect
        System.exit(0);
    }

    public void start() {
        // Initialize the window and OpenGL context
        Window.init(800, 600, "EaglecraftY");

        // Initialize the renderer (after OpenGL context is created)
        renderer.init();

        // Game loop
        while (!Window.shouldClose()) {
            // Handle input
            inputHandler.update();

            // Check if the game is paused
            if (inputHandler.isEscapePressed()) {
                isPaused = !isPaused;
                uiManager.getPauseMenu().setVisible(isPaused); // Show or hide the pause menu
            }

            if (isPaused) {
                // Update the UI
                uiManager.update();
            } else {
                // Update the player
                player.update();

                // Update the world
                world.update();
            }

            // Render the world
            renderer.render(world, Window.getWidth(), Window.getHeight());

            // Render the UI
            uiManager.render();

            // Swap buffers and poll events
            Window.swapBuffers();
            Window.pollEvents();
        }

        // Clean up resources
        renderer.cleanup();
        Window.terminate();

        // Log shutdown message
        if (SharedConstants.ENABLE_LOGGING) {
            System.out.println("EaglecraftY has been shut down.");
        }
    }

    public static void main(String[] args) {
        // Create the game instance and start it
        Game game = new Game();
        game.start();
    }
}