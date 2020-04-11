package com.example.RelaxWithGems.Models;

import java.lang.reflect.Array;

public class Item {
    public String name;
    public String desciption;
    public String imagePath;
    public String[] tags;

    public Item(String name, String desciption, String imagePath, String[] tags) {
        this.name = name;
        this.desciption = desciption;
        this.imagePath = imagePath;
        this.tags = tags;
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
