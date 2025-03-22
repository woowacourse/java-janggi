package domain.piece;

import static domain.direction.PieceDirection.CANNON;
import static domain.direction.PieceDirection.CHARIOT;
import static domain.direction.PieceDirection.CHO_SOLDIER;
import static domain.direction.PieceDirection.ELEPHANT;
import static domain.direction.PieceDirection.GUARD;
import static domain.direction.PieceDirection.HAN_SOLDIER;
import static domain.direction.PieceDirection.HORSE;

import domain.direction.PieceDirection;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.piece.category.Soldier;
import domain.spatial.Position;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PieceInit {

    KING(new King(Position.of(5, 2), PieceDirection.KING.get()),
            new King(Position.of(5, 9), PieceDirection.KING.get())),
    CHARIOT1(new Chariot(Position.of(1, 1), CHARIOT.get()), new Chariot(Position.of(1, 10), CHARIOT.get())),
    CHARIOT2(new Chariot(Position.of(9, 1), CHARIOT.get()), new Chariot(Position.of(9, 10), CHARIOT.get())),
    CANNON1(new Cannon(Position.of(2, 3), CANNON.get()), new Cannon(Position.of(2, 8), CANNON.get())),
    CANNON2(new Cannon(Position.of(8, 3), CANNON.get()), new Cannon(Position.of(8, 8), CANNON.get())),
    HORSE1(new Horse(Position.of(3, 1), HORSE.get()), new Horse(Position.of(3, 10), HORSE.get())),
    HORSE2(new Horse(Position.of(7, 1), HORSE.get()), new Horse(Position.of(7, 10), HORSE.get())),
    ELEPHANT1(new Elephant(Position.of(2, 1), ELEPHANT.get()), new Elephant(Position.of(2, 10), ELEPHANT.get())),
    ELEPHANT2(new Elephant(Position.of(8, 1), ELEPHANT.get()), new Elephant(Position.of(8, 10), ELEPHANT.get())),
    GUARD1(new Guard(Position.of(4, 1), GUARD.get()), new Guard(Position.of(4, 10), GUARD.get())),
    GUARD2(new Guard(Position.of(6, 1), GUARD.get()), new Guard(Position.of(6, 10), GUARD.get())),
    PAWN1(new Soldier(Position.of(1, 4), HAN_SOLDIER.get()), new Soldier(Position.of(1, 7), CHO_SOLDIER.get())),
    PAWN2(new Soldier(Position.of(3, 4), HAN_SOLDIER.get()), new Soldier(Position.of(3, 7), CHO_SOLDIER.get())),
    PAWN3(new Soldier(Position.of(5, 4), HAN_SOLDIER.get()), new Soldier(Position.of(5, 7), CHO_SOLDIER.get())),
    PAWN4(new Soldier(Position.of(7, 4), HAN_SOLDIER.get()), new Soldier(Position.of(7, 7), CHO_SOLDIER.get())),
    PAWN5(new Soldier(Position.of(9, 4), HAN_SOLDIER.get()), new Soldier(Position.of(9, 7), CHO_SOLDIER.get())),
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
