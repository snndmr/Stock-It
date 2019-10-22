package com.snn.stockit;

import java.util.ArrayList;

class Room {
    static ArrayList<Room> rooms = new ArrayList<>();

    private int room_image;
    private String room_name;
    private String room_image_path;
    private ArrayList<Item> items;

    Room(int room_image, String room_name, String room_image_path, ArrayList<Item> items) {
        this.room_image = room_image;
        this.room_name = room_name;
        this.room_image_path = room_image_path;
        this.items = items;
    }

    static ArrayList<Room> getRooms() {
        return rooms;
    }

    static void setRooms(ArrayList<Room> rooms) {
        Room.rooms = rooms;
    }

    int getRoom_image() {
        return room_image;
    }

    void setRoom_image(int room_image) {
        this.room_image = room_image;
    }

    String getRoom_name() {
        return room_name;
    }

    void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    String getRoom_image_path() {
        return room_image_path;
    }

    void setRoom_image_path(String room_image_path) {
        this.room_image_path = room_image_path;
    }

    ArrayList<Item> getItems() {
        return items;
    }

    void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
