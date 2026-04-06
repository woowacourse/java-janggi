package domain.piece;

public record PieceId(Long value) {
    public static final PieceId UNASSIGNED = new PieceId(null);
}
