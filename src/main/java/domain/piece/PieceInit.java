package domain.piece;

import static domain.direction.PieceDirection.ADVISOR;
import static domain.direction.PieceDirection.CANNON;
import static domain.direction.PieceDirection.CHO_PAWN;
import static domain.direction.PieceDirection.ELEPHANT;
import static domain.direction.PieceDirection.HAN_PAWN;
import static domain.direction.PieceDirection.HORSE;
import static domain.direction.PieceDirection.ROOK;

import domain.direction.PieceDirection;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.General;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.Soldier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PieceInit {

    KING(new General(5, 2, PieceDirection.KING.get()), new General(5, 9, PieceDirection.KING.get())),
    ROOK1(new Chariot(1, 1, ROOK.get()), new Chariot(1, 10, ROOK.get())),
    ROOK2(new Chariot(9, 1, ROOK.get()), new Chariot(9, 10, ROOK.get())),
    CANNON1(new Cannon(2, 3, CANNON.get()), new Cannon(2, 8, CANNON.get())),
    CANNON2(new Cannon(8, 3, CANNON.get()), new Cannon(8, 8, CANNON.get())),
    HORSE1(new Horse(3, 1, HORSE.get()), new Horse(3, 10, HORSE.get())),
    HORSE2(new Horse(7, 1, HORSE.get()), new Horse(7, 10, HORSE.get())),
    ELEPHANT1(new Elephant(2, 1, ELEPHANT.get()), new Elephant(2, 10, ELEPHANT.get())),
    ELEPHANT2(new Elephant(8, 1, ELEPHANT.get()), new Elephant(8, 10, ELEPHANT.get())),
    ADVISOR1(new Guard(4, 1, ADVISOR.get()), new Guard(4, 10, ADVISOR.get())),
    ADVISOR2(new Guard(6, 1, ADVISOR.get()), new Guard(6, 10, ADVISOR.get())),
    PAWN1(new Soldier(1, 4, HAN_PAWN.get()), new Soldier(1, 7, CHO_PAWN.get())),
    PAWN2(new Soldier(3, 4, HAN_PAWN.get()), new Soldier(3, 7, CHO_PAWN.get())),
    PAWN3(new Soldier(5, 4, HAN_PAWN.get()), new Soldier(5, 7, CHO_PAWN.get())),
    PAWN4(new Soldier(7, 4, HAN_PAWN.get()), new Soldier(7, 7, CHO_PAWN.get())),
    PAWN5(new Soldier(9, 4, HAN_PAWN.get()), new Soldier(9, 7, CHO_PAWN.get())),
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
                .collect(Collectors.toList());
    }

    public static List<Piece> initChoPieces() {
        return Arrays.stream(values())
                .map(piece -> piece.choPiece)
                .collect(Collectors.toList());
    }
}
