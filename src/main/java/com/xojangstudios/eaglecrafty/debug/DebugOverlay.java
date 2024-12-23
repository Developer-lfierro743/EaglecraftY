package com.xojangstudios.eaglecrafty.debug;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.InputStream;

public class DebugOverlay {

    private TrueTypeFont font;

    public DebugOverlay() {
        // Load a font (e.g., Minecraft-style font)
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("MinecraftEvenings-lgvPd.ttf");
            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont(12f); // Set font size
            font = new TrueTypeFont(awtFont, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Update logic (if needed)
    }

    public void render() {
        // Set up OpenGL for 2D rendering
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 600, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // Render the text in the top-right corner
        String message = "EaglecraftY Pre-Development v0.0 Unlicensed Copy :(\n" +
                         "Or logged in from another location\n" +
                         "Purchase at https://xojangstudioszb.infinityfreeapp.com/";

        // Calculate the position to align text to the top-right corner
        int textWidth = font.getWidth(message);
        int x = 800 - textWidth - 10; // 10 pixels from the right edge
        int y = 10; // 10 pixels from the top edge

        // Render the text
        font.drawString(x, y, message, Color.white);
    }
}