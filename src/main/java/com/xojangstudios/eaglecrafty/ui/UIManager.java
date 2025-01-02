package com.xojangstudios.eaglecrafty.ui;

import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private List<UIElement> elements;
    private Panel pauseMenu;

    public UIManager() {
        elements = new ArrayList<>();
    }

    public void addElement(UIElement element) {
        elements.add(element);
        if (element instanceof Panel && ((Panel) element).getName().equals("PauseMenu")) {
            pauseMenu = (Panel) element;
        }
    }

    public void update() {
        for (UIElement element : elements) {
            element.update();
        }
    }

    public void render() {
        for (UIElement element : elements) {
            element.render();
        }
    }

    public Panel getPauseMenu() {
        return pauseMenu;
    }
}