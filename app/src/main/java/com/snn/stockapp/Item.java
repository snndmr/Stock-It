package com.snn.stockapp;

class Item {
    private String name;
    private String location;
    private int piece;
    private int image;

    Item(String name, String location) {
        this.name = name;
        this.location = location;
        this.piece = 1;
        this.image = R.drawable.room_test_1;
    }

    Item(String name, String location, int piece) {
        this.name = name;
        this.location = location;
        this.piece = piece;
        this.image = R.drawable.room_test_1;
    }

    Item(String name, String location, int piece, int image) {
        this.name = name;
        this.location = location;
        this.piece = piece;
        this.image = image;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }

    int getPiece() {
        return piece;
    }

    void setPiece(int piece) {
        this.piece = piece;
    }

    int getImage() {
        return image;
    }

    void setImage(int image) {
        this.image = image;
    }
}
