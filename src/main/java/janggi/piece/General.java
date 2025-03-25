package janggi.piece;

public class General extends Piece {
    private static final String NAME = "장";

    public General(Team team) {
        super(team);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == PieceType.GENERAL;
    }
}
