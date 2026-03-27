package model.board;

public enum Country {
    HAN("한", "\u001B[31m"),
    CHO("초", "\u001B[32m");

    public static final String RESET = "\u001B[0m";
    private final String title;
    private final String color;

    Country(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public String title() {
        return title;
    }

    public String color() {
        return color;
    }
}
