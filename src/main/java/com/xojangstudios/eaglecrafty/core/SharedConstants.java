package com.xojangstudios.eaglecrafty.core;

public class SharedConstants {
    // Build types
    public static final String BUILD_TYPE_DEVELOPER = "Developer";
    public static final String BUILD_TYPE_PRE_RELEASE = "Pre-Release";
    public static final String BUILD_TYPE_RELEASE = "Release";

    // Current build type
    public static final String BUILD_TYPE = BUILD_TYPE_DEVELOPER; // Change this based on the build

    // Version information
    public static final String VERSION = "1.0.0";
    public static final int VERSION_BUILD = 1;

    // Experimental features (enabled/disabled)
    public static final boolean EXPERIMENTAL_FEATURE_1 = false; // Example feature
    public static final boolean EXPERIMENTAL_FEATURE_2 = false; // Example feature

    // Developer-only features (enabled/disabled)
    public static final boolean DEVELOPER_FEATURE_1 = BUILD_TYPE.equals(BUILD_TYPE_DEVELOPER); // Enabled only in developer builds
    public static final boolean DEVELOPER_FEATURE_2 = BUILD_TYPE.equals(BUILD_TYPE_DEVELOPER); // Enabled only in developer builds

    // World and seed settings
    public static final long DEFAULT_WORLD_SEED = 123456789L; // Default seed for world generation

    // Debug mode (enabled/disabled)
    public static final boolean DEBUG_MODE = BUILD_TYPE.equals(BUILD_TYPE_DEVELOPER); // Enabled only in developer builds

    // Logging settings
    public static final boolean ENABLE_LOGGING = true; // Enable or disable logging
}