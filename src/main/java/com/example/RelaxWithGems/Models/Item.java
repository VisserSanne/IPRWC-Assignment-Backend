package com.example.RelaxWithGems.Models;

import java.lang.reflect.Array;

public class Item {
    public String id;
    public String name;
    public String desciption;
    public String imagePath;

    public Item(String id, String name, String desciption, String imagePath) {
        this.id = id;
        this.name = name;
        this.desciption = desciption;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
