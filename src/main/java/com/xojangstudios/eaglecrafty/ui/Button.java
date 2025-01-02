package com.xojangstudios.eaglecrafty.ui;

import org.joml.Vector2f;

public class Button extends UIElement {
    private String text;
    private boolean isClicked;
    private Runnable onClick;

    public Button(Vector2f position, Vector2f size, String text, Runnable onClick) {
        super(position, size);
        this.text = text;
        this.isClicked = false;
        this.onClick = onClick;
    }

    @Override
    public void update() {
        // Update logic for the button (e.g., check for clicks)
        if (isClicked) {
            onClick.run();
            isClicked = false; // Reset the click state
        }
    }

    @Override
    public void render() {
        // Render the button (e.g., draw the button and text)
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}