package domain.piece;

import java.util.Arrays;
import java.util.List;

public enum PieceInit {

    KING(new King(5, 2), new King(5, 9)),
    ROOK1(new Rook(1, 1), new Rook(1, 10)),
    ROOK2(new Rook(9, 1), new Rook(9, 10)),
    CANNON1(new Cannon(2, 3), new Cannon(2, 8)),
    CANNON2(new Cannon(8, 3), new Cannon(8, 8)),
    HORSE1(new Horse(3, 1), new Horse(3, 10)),
    HORSE2(new Horse(7, 1), new Horse(7, 10)),
    ELEPHANT1(new Elephant(2, 1), new Elephant(2, 10)),
    ELEPHANT2(new Elephant(8, 1), new Elephant(8, 10)),
    ADVISOR1(new Advisor(4, 1), new Advisor(4, 10)),
    ADVISOR2(new Advisor(6, 1), new Advisor(6, 10)),
    PAWN1(new Pawn(1, 4), new Pawn(1, 7)),
    PAWN2(new Pawn(3, 4), new Pawn(3, 7)),
    PAWN3(new Pawn(5, 4), new Pawn(5, 7)),
    PAWN4(new Pawn(7, 4), new Pawn(7, 7)),
    PAWN5(new Pawn(9, 4), new Pawn(9, 7)),
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
