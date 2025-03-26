package janggi.piece;

public class Elephant extends Piece {
    private static final PieceType TYPE = PieceType.ELEPHANT;

    public Elephant(Team team) {
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
