package domain;

import java.util.List;

public enum ChessPieceType {

    CHARIOT(
            List.of(new ChessPosition(0, 0), new ChessPosition(0, 8)),
            List.of(new ChessPosition(9, 0), new ChessPosition(9, 8))
    ),
    HORSE(
            List.of(new ChessPosition(0, 1), new ChessPosition(0, 7)),
            List.of(new ChessPosition(9, 1), new ChessPosition(9, 7))
    ),
    ELEPHANT(
            List.of(new ChessPosition(0, 2), new ChessPosition(0, 6)),
            List.of(new ChessPosition(9, 2), new ChessPosition(9, 6))
    ),
    CANNON(
            List.of(new ChessPosition(2, 1), new ChessPosition(2, 7)),
            List.of(new ChessPosition(7, 1), new ChessPosition(7, 7))
    ),
    GUARD(
            List.of(new ChessPosition(0, 3), new ChessPosition(0, 5)),
            List.of(new ChessPosition(9, 3), new ChessPosition(9, 5))
    ),
    PAWN(
            List.of(new ChessPosition(3, 0), new ChessPosition(3, 2), new ChessPosition(3, 4), new ChessPosition(3, 6), new ChessPosition(3, 8)),
            List.of(new ChessPosition(6, 0), new ChessPosition(6, 2), new ChessPosition(6, 4), new ChessPosition(6, 6), new ChessPosition(6, 8))
    ),
    KING(
            List.of(new ChessPosition(1, 4)),
            List.of(new ChessPosition(8, 4))
    ),
    NONE(List.of(), List.of());

    private final List<ChessPosition> redPosition;
    private final List<ChessPosition> bluePosition;

    ChessPieceType(final List<ChessPosition> redPosition, final List<ChessPosition> bluePosition) {
        this.redPosition = redPosition;
        this.bluePosition = bluePosition;
    }

    public List<ChessPosition> getRedPosition() {
        return redPosition;
    }

    public List<ChessPosition> getBluePosition() {
        return bluePosition;
    }
}
