package com.pixibo.zalora.Model;

public class BrandModel {

    private String name ,id;

    public BrandModel() {
    }

    public BrandModel(String name ,String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
