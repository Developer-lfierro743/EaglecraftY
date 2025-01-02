package com.xojangstudios.eaglecrafty.ui;

import org.joml.Vector2f;

public abstract class UIElement {
    protected Vector2f position;
    protected Vector2f size;

    public UIElement(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
    }

    public abstract void update();

    public abstract void render();

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }
}