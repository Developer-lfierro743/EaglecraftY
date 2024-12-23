
public class GameModeHandler {
    // ...existing code...

    public void setGameMode(GameMode gameMode) {
        // ...existing code...
        if (gameMode == GameMode.ADVENTURE_PLUS) {
            // Custom logic for AdventurePlus mode
            enableAdventurePlusFeatures();
        }
        // ...existing code...
    }

    private void enableAdventurePlusFeatures() {
        // Implement the features specific to AdventurePlus mode
    }

    // ...existing code...
}