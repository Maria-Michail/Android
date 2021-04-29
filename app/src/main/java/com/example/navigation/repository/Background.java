package com.example.navigation.repository;

public class Background {
    private String background;

    public Background(String background) {
        this.background = background;
    }

    public Background() {}

    public String getBody() {
        return background;
    }

    public void setBody(String name) {
        this.background = name;
    }
}
