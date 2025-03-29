package domain.piece;

public class PieceFactory {

    public static Piece createPiece(PieceType type, PieceColor color) {
        switch (type) {
            case CHARIOT:
                return new Chariot(color);
            case HORSE:
                return new Horse(color);
            case ELEPHANT:
                return new Elephant(color);
            case GUARD:
                return new Guard(color);
            case GENERAL:
                return new General(color);
            case CANNON:
                return new Cannon(color);
            case SOLDIER:
                return new Soldier(color);
            default:
                throw new IllegalArgumentException("존재하지 않는 기물입니다.");
        }
    }
}
