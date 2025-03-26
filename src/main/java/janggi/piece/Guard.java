package janggi.piece;

public class Guard extends Piece {
    private static final PieceType TYPE = PieceType.GUARD;

    public Guard(Team team) {
        super(team);
    }

    @Override
    protected String getName() {
        return TYPE.getName();
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == TYPE;
    }
}
