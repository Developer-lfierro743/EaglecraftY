package com.xojangstudios.eaglecrafty.utils;

public class GameLogger {

    public static void log(String message) {
        System.out.println("[" + System.currentTimeMillis() + "] " + message);
    }

    public static void info(String message) {
        System.out.println("[" + System.currentTimeMillis() + "] [INFO] " + message);
    }

    public static void warning(String message) {
        System.out.println("[" + System.currentTimeMillis() + "] [WARN] " + message);
    }

    public static void error(String message) {
        System.out.println("[" + System.currentTimeMillis() + "] [ERROR] " + message);
    }

}
