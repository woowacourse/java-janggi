package domain;

import domain.piece.PieceType;
import java.util.List;

public enum InitialPosition {
    SOLDIER(
            PieceType.SOLDIER,
            List.of(new Position(0, 3), new Position(2, 3), new Position(4, 3), new Position(6, 3), new Position(8, 3)),
            List.of(new Position(0, 6), new Position(2, 6), new Position(4, 6), new Position(6, 6), new Position(8, 6))
    ),
    GUARD(
            PieceType.GUARD,
            List.of(new Position(3, 0), new Position(5, 0)),
            List.of(new Position(3, 9), new Position(5, 9))
    ),
    CANNON(
            PieceType.CANNON,
            List.of(new Position(1, 2), new Position(7, 2)),
            List.of(new Position(1, 7), new Position(7, 7))
    ),
    CHARIOT(
            PieceType.CHARIOT,
            List.of(new Position(0, 0), new Position(8, 0)),
            List.of(new Position(0, 9), new Position(8, 9))
    ),
    GENERAL(
            PieceType.GENERAL,
            List.of(new Position(4, 1)),
            List.of(new Position(4, 8))
    );

    private final PieceType pieceType;
    private final List<Position> choPositions;
    private final List<Position> hanPositions;

    InitialPosition(PieceType pieceType, List<Position> choPositions, List<Position> hanPositions) {
        this.pieceType = pieceType;
        this.choPositions = choPositions;
        this.hanPositions = hanPositions;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public List<Position> getChoPositions() {
        return choPositions;
    }

    public List<Position> getHanPositions() {
        return hanPositions;
    }
}
