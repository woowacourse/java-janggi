import java.util.Objects;

public class Piece {
    private final PieceType pieceType;
    private final TeamType teamType;
    private static final Piece emptyPieceCache = new Piece(PieceType.EMPTY, TeamType.NONE);

    public Piece(final PieceType pieceType, final TeamType teamType) {
        this.pieceType = pieceType;
        this.teamType = teamType;
    }

    public static Piece createEmpty() {
        return emptyPieceCache;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType && teamType == piece.teamType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, teamType);
    }
}
