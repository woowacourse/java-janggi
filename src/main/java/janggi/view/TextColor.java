package janggi.view;

public enum TextColor {
    DEFAULT("\u001B[0m"),
    BLUE("\u001B[34m"),
    RED("\u001B[31m"),
    YELLOW("\u001B[33m"),
    ;

    private final String color;

    TextColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
