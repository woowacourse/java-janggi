package janggi.piece;

public class General extends Piece {
    private static final PieceType TYPE = PieceType.GENERAL;

    public General(Team team) {
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
