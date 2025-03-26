package janggi.piece;

public class Horse extends Piece {
    private static final PieceType TYPE = PieceType.HORSE;


    public Horse(Team team) {
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
