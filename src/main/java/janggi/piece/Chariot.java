package janggi.piece;

public class Chariot extends Piece {
    private static final String NAME = "차";

    public Chariot(Team team) {
        super(team);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == PieceType.CHARIOT;
    }
}
