public enum GameMode {
    // ...existing code...
    ADVENTURE_PLUS("adventure_plus", 4);
    // ...existing code...

    private final String name;
    private final int id;

    GameMode(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // ...existing code...
}
