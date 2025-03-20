package domain.piece;

public enum PieceColor {

    RED("\033[0;31m"),
    BLUE("\033[0;34m");

    private final String color;

    PieceColor(final String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
