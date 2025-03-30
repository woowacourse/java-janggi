package domain.piece;

import java.util.function.Function;

public enum PieceType {
    CANNON(7, Cannon::new),
    CHARIOT(13, Chariot::new),
    ELEPHANT(3, Elephant::new),
    GUARD(3, Guard::new),
    HORSE(5, Horse::new),
    KING(0, King::new),
    SOLDIER(2, Soldier::new);

    private final double score;
    private final Function<TeamType, Piece> createPiece;

    PieceType(int score, Function<TeamType, Piece> createPiece) {
        this.score = score;
        this.createPiece = createPiece;
    }

    public static Piece createPiece(PieceType pieceType, TeamType teamType) {
        return pieceType.createPiece.apply(teamType);
    }

    public double getScore() {
        return score;
    }
}
