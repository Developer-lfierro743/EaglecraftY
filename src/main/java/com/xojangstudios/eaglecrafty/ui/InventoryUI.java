package com.xojangstudios.eaglecrafty.ui;

import java.util.ArrayList;
import java.util.List;
import com.xojangstudios.eaglecrafty.items.Item;


public class InventoryUI {
    private static final int MAX_SLOTS = 50;
    private List<Item> slots;

    public InventoryUI() {
        slots = new ArrayList<>(MAX_SLOTS);
        for (int i = 0; i < MAX_SLOTS; i++) {
            slots.add(null); // Initialize all slots to null
        }
    }

    public boolean addItem(Item item) {
        for (int i = 0; i < MAX_SLOTS; i++) {
            if (slots.get(i) == null) {
                slots.set(i, item);
                return true;
            }
        }
        return false; // Inventory is full
    }

    public boolean removeItem(Item item) {
        for (int i = 0; i < MAX_SLOTS; i++) {
            if (slots.get(i) != null && slots.get(i).equals(item)) {
                slots.set(i, null);
                return true;
            }
        }
        return false; // Item not found
    }

    public Item getItem(int slot) {
        if (slot >= 0 && slot < MAX_SLOTS) {
            return slots.get(slot);
        }
        return null; // Invalid slot
    }

    public int getMaxSlots() {
        return MAX_SLOTS;
    }

    // Additional methods to manage inventory can be added here
}