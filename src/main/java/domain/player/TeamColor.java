package domain.player;

public enum TeamColor {

    RED("\033[0;31m"),
    BLUE("\033[0;34m");

    private final String color;

    TeamColor(final String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
