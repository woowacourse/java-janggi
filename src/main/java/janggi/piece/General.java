package janggi.piece;

public class General extends Piece {
    private final PieceType pieceType;

    public General(Team team) {
        super(team);
        this.pieceType = PieceType.GENERAL;
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
