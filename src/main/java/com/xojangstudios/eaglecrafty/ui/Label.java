package com.xojangstudios.eaglecrafty.ui;

import org.joml.Vector2f;

public class Label extends UIElement {
    private String text;

    public Label(Vector2f position, Vector2f size, String text) {
        super(position, size);
        this.text = text;
    }

    @Override
    public void update() {
        // Update logic for the label (if any)
    }

    @Override
    public void render() {
        // Render the label (e.g., draw the text)
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
