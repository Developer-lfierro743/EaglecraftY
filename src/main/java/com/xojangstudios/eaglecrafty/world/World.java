package com.xojangstudios.eaglecrafty.world;

import java.util.ArrayList;
import java.util.List;

import com.xojangstudios.eaglecrafty.core.Game;


public class World {
    private String name;
    private int width;
    private int height;
    private List<String> entities;

    public World(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
    }

    public World(Game game) {
        //TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getEntities() {
        return entities;
    }

    public void addEntity(String entity) {
        entities.add(entity);
    }

    public void removeEntity(String entity) {
        entities.remove(entity);
    }

    @Override
    public String toString() {
        return "World{" +
                "name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", entities=" + entities +
                '}';
    }
}
