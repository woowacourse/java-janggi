package janggi.piece;

public class Chariot extends Piece {
    private static final PieceType TYPE = PieceType.CHARIOT;

    public Chariot(Team team) {
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
