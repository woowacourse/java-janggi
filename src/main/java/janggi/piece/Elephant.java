package janggi.piece;

public class Elephant extends Piece {
    private static final String NAME = "상";

    public Elephant(Team team) {
        super(team);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == PieceType.ELEPHANT;
    }
}
