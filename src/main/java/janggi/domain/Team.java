package janggi.domain;

public enum Team {
    RED("한", "\u001B[31m"),
    GREEN("초", "\u001B[32m"),
    ;

    public static final String COLOR_RESET = "\u001B[0m";

    private final String country;
    private final String color;

    Team(final String country, final String color) {
        this.country = country;
        this.color = color;
    }

    public static int decideRow(final int row, final Team team) {
        if (team.isGreen()) {
            int rowFlipBase = 11;
            return rowFlipBase - row;
        }
        return row;
    }

    public boolean isRed() {
        return this == RED;
    }

    public boolean isGreen() {
        return this == GREEN;
    }

    public String getColor() {
        return color;
    }

    public String getCountry() {
        return country;
    }

    public static Team getEnemy(Team team) {
        if (team.isRed()) {
            return GREEN;
        }
        return RED;
    }
}
