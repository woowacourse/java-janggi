package domain.enums;

public enum Country {
    CHO("초나라", 1,"\u001B[32m"),
    HAN("한나라", -1,"\u001B[31m"),
    NONE("없음", 0,"\u001B[0m");

    private final String name;
    private final int forward;
    private final String color;

    Country(String name, int forward, String color) {
        this.name = name;
        this.forward = forward;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getForward() {
        return forward;
    }
}
