package janggi.piece;

public class Horse extends Piece {
    private static final String NAME = "마";

    public Horse(Team team) {
        super(team);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == PieceType.HORSE;
    }
}
