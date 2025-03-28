package janggi.piece;

public class Guard extends Piece {
    private final PieceType pieceType;

    public Guard(Team team) {
        super(team);
        this.pieceType = PieceType.GUARD;
    }

    @Override
    protected String getName() {
        return pieceType.getName();
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }
}
