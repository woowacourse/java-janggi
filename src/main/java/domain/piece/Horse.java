package domain.piece;

public class Horse extends Piece {

    private static final String NAME = "h";

    public Horse(final int row, final int column) {
        super(row, column);
    }

    public String getName() {
        return NAME;
    }
}
