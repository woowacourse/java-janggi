package domain.piece;

public class PieceFactory {

    public static Piece createPiece(PieceType type, PieceColor color) {
        return switch (type) {
            case CHARIOT -> new Chariot(color);
            case HORSE -> new Horse(color);
            case ELEPHANT -> new Elephant(color);
            case GUARD -> new Guard(color);
            case GENERAL -> new General(color);
            case CANNON -> new Cannon(color);
            case SOLDIER -> new Soldier(color);
            default -> throw new IllegalArgumentException("존재하지 않는 기물입니다.");
        };
    }
}
