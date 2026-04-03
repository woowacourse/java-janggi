package domain.piece;

public enum Side {

    CHO("초", "\\u001B[34m"),
    HAN("한", "\\u001B[31m");

    private final String name;
    private final String color;

    Side(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
