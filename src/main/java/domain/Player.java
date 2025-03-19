package domain;

public class Player {

    private final String name;
    private final PieceColor color;

    public Player(final String name, final PieceColor color) {
        this.name = name;
        this.color = color;
    }

    public PieceColor getColor() {
        return color;
    }
}
