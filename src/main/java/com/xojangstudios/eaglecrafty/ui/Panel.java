package com.xojangstudios.eaglecrafty.ui;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Panel extends UIElement {
    private List<UIElement> children;
    private boolean isVisible;
    private String name;

    public Panel(Vector2f position, Vector2f size, String name) {
        super(position, size);
        this.children = new ArrayList<>();
        this.isVisible = true;
        this.name = name;
    }

    public void addChild(UIElement element) {
        children.add(element);
    }

    @Override
    public void update() {
        if (isVisible) {
            for (UIElement element : children) {
                element.update();
            }
        }
    }

    @Override
    public void render() {
        if (isVisible) {
            // Render the panel (e.g., draw the background)
            for (UIElement element : children) {
                element.render();
            }
        }
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getName() {
        return name;
    }
}