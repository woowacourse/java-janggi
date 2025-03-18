package domain.piece;

public class Elephant extends Piece {

    private static final String NAME = "e";

    public Elephant(final int row, final int column) {
        super(row, column);
    }

    public String getName() {
        return NAME;
    }
}
