package com.snn.stockapp;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class Room implements Serializable {
    static ArrayList<Room> rooms;

    private int image;
    private String name;
    private ArrayList<Item> items;

    Room(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.image = R.drawable.room_test_3;
    }

    static void saveData() {
        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();

        String rooms_string = new Gson().toJson(rooms);

        editor.putString("Rooms", rooms_string);
        editor.apply();
    }

    static List<Room> loadData() {
        rooms = new ArrayList<>();
        String rooms_string = MainActivity.sharedPreferences.getString("Rooms", "");

        if (!rooms_string.isEmpty()) {
            Type type = new TypeToken<List<Room>>() {
            }.getType();
            return new Gson().fromJson(rooms_string, type);
        }
        return Room.rooms;
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
