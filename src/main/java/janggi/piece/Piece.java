package janggi.piece;

public abstract class Piece {
    private final Position position;
    protected final TeamType teamType;

    //TODO: 빈 기물 구현
    private static final Piece emptyPieceCache = null;

    public Piece(final Position position, final TeamType teamType) {
        this.position = position;
        this.teamType = teamType;
    }

    public Position getPosition() {
        return position;
    }

    public static Piece createEmpty() {
        return emptyPieceCache;
    }
}
