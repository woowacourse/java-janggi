package domain;

public enum Team {
    HAN("한(han)", "\033[0;31m"),
    CHO("초(cho)", "\033[0;34m");

    private final String name;
    private final String color;

    Team(final String name, final String color) {
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
