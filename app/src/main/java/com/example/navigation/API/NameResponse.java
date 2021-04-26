package com.example.navigation.API;

public class NameResponse {
    private Data data;
    private NameDays namedays;

    public String getNameDay() {
        return data.namedays.dk;
    }

    private static class Data{
        private NameDays namedays;
    }

    private static class NameDays {
        private String dk;
    }

}
