package domain.board;

import domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public enum ElephantSetup {

    INNER(Map.of(
            Position.of(1, 2), PieceType.HORSE,
            Position.of(1, 3), PieceType.ELEPHANT,
            Position.of(1, 7), PieceType.ELEPHANT,
            Position.of(1, 8), PieceType.HORSE
    )),

    OUTER(Map.of(
            Position.of(1, 2), PieceType.ELEPHANT,
            Position.of(1, 3), PieceType.HORSE,
            Position.of(1, 7), PieceType.HORSE,
            Position.of(1, 8), PieceType.ELEPHANT
    )),

    RIGHT(Map.of(
            Position.of(1, 2), PieceType.HORSE,
            Position.of(1, 3), PieceType.ELEPHANT,
            Position.of(1, 7), PieceType.HORSE,
            Position.of(1, 8), PieceType.ELEPHANT
    )),

    LEFT(Map.of(
            Position.of(1, 2), PieceType.ELEPHANT,
            Position.of(1, 3), PieceType.HORSE,
            Position.of(1, 7), PieceType.ELEPHANT,
            Position.of(1, 8), PieceType.HORSE
    ));


    private final Map<Position, PieceType> piecePositions;

    ElephantSetup(final Map<Position, PieceType> piecePositions) {
        this.piecePositions = piecePositions;
    }

    public Map<Position, PieceType> getPiecePositions() {
        return piecePositions;
    }

    public static List<ElephantSetup> all() {
        return List.of(values());
    }
}
