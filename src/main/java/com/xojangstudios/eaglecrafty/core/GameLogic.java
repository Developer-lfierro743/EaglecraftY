import java.util.Scanner;

package com.xojangstudios.eaglecrafty.core;


public class InputHandler {
    private GameLogic game;

    public InputHandler(GameLogic game) {
        this.game = game;
    }

    public void handleInput() {
        Scanner scanner = new Scanner(System.in);
        while (game.isRunning()) {
            System.out.println("Enter command (start/stop/score/level): ");
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "start":
                    game.start();
                    break;
                case "stop":
                    game.stop();
                    break;
                case "score":
                    System.out.println("Current score: " + game.getScore());
                    break;
                case "level":
                    System.out.println("Current level: " + game.getLevel());
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
        scanner.close();
    }
}
