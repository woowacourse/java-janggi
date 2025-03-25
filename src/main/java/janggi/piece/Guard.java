package janggi.piece;

public class Guard extends Piece {
    private static final String NAME = "사";

    public Guard(Team team) {
        super(team);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == PieceType.GUARD;
    }
}
