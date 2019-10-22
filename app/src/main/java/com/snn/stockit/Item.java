package com.snn.stockit;

class Item {
    private int item_piece;
    private int item_image;
    private String item_name;
    private String item_location;
    private String item_image_path;
    private String item_description;

    Item(int item_piece, int item_image, String item_name, String item_location, String item_image_path, String item_description) {
        this.item_piece = item_piece;
        this.item_image = item_image;
        this.item_name = item_name;
        this.item_location = item_location;
        this.item_image_path = item_image_path;
        this.item_description = item_description;
    }

    int getItem_piece() {
        return item_piece;
    }

    void setItem_piece(int item_piece) {
        this.item_piece = item_piece;
    }

    int getItem_image() {
        return item_image;
    }

    void setItem_image(int item_image) {
        this.item_image = item_image;
    }

    String getItem_name() {
        return item_name;
    }

    void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    String getItem_location() {
        return item_location;
    }

    void setItem_location(String item_location) {
        this.item_location = item_location;
    }

    String getItem_image_path() {
        return item_image_path;
    }

    void setItem_image_path(String item_image_path) {
        this.item_image_path = item_image_path;
    }

    String getItem_description() {
        return item_description;
    }

    void setItem_description(String item_description) {
        this.item_description = item_description;
    }
}