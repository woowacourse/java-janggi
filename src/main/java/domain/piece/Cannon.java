package domain.piece;

public class Cannon extends Piece {

    private static final String NAME = "c";

    public Cannon(final int row, final int column) {
        super(row, column);
    }

    public String getName() {
        return NAME;
    }
}
