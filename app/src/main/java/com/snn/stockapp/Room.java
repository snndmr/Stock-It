package com.snn.stockapp;

import java.io.Serializable;
import java.util.ArrayList;

class Room implements Serializable {
    private int image;
    private String name;
    private ArrayList<Item> items;

    Room(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.image = R.drawable.room_test_1;
    }

    Room(String name, int image) {
        this.name = name;
        this.image = image;
        this.items = new ArrayList<>();
    }

    int getImage() {
        return image;
    }

    void setImage(int image) {
        this.image = image;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    ArrayList<Item> getItems() {
        return items;
    }

    void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
