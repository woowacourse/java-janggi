package domain.piece;

import java.util.Arrays;
import java.util.List;

public enum PieceInit {

    KING(new King(1, 1), new King(5, 6)),
    ROOK1(new Rook(1, 1), new Rook(1, 1)),
    ROOK2(new Rook(1, 1), new Rook(1, 1)),
    CANNON1(new Cannon(1, 1), new Cannon(1, 1)),
    CANNON2(new Cannon(1, 1), new Cannon(1, 1)),
    HORSE1(new Horse(1, 1), new Horse(1, 1)),
    HORSE2(new Horse(1, 1), new Horse(1, 1)),
    ELEPHANT1(new Elephant(1, 1), new Elephant(1, 1)),
    ELEPHANT2(new Elephant(1, 1), new Elephant(1, 1)),
    ADVISOR1(new Advisor(1, 1), new Advisor(1, 1)),
    ADVISOR2(new Advisor(1, 1), new Advisor(1, 1)),
    PAWN1(new Pawn(1, 1), new Pawn(1, 1)),
    PAWN2(new Pawn(1, 1), new Pawn(1, 1)),
    PAWN3(new Pawn(1, 1), new Pawn(1, 1)),
    PAWN4(new Pawn(1, 1), new Pawn(1, 1)),
    PAWN5(new Pawn(1, 1), new Pawn(1, 1)),
    ;

    private final Piece hanPiece;
    private final Piece choPiece;

    PieceInit(final Piece hanPiece, final Piece choPiece) {
        this.hanPiece = hanPiece;
        this.choPiece = choPiece;
    }

    public static List<Piece> initHanPieces() {
        return Arrays.stream(values())
                .map(piece -> piece.hanPiece)
                .toList();
    }

    public static List<Piece> initChoPieces() {
        return Arrays.stream(values())
                .map(piece -> piece.choPiece)
                .toList();
    }
}
