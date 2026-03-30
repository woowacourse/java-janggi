package domain;

public enum Country {
    CHO("초나라", "\u001B[32m"),
    HAN("한나라", "\u001B[31m"),
    NONE("없음", "\u001B[0m");

    private final String name;
    private final String color;

    Country(String name, String color) {
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
