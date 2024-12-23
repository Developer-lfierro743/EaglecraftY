package com.xojangstudios.eaglecrafty.client.gamemode;

public class SurvivalMode {

    private int health;
    private int hunger;
    private int experience;

    public SurvivalMode() {
        this.health = 20; // Default health
        this.hunger = 20; // Default hunger
        this.experience = 0; // Default experience
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void eatFood(int foodValue) {
        this.hunger += foodValue;
        if (this.hunger > 20) {
            this.hunger = 20;
        }
    }

    public void gainExperience(int exp) {
        this.experience += exp;
    }

    public boolean isAlive() {
        return this.health > 0;
    }
}