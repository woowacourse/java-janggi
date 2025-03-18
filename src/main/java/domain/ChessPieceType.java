package domain;

import java.util.List;
import java.util.function.Function;

public enum ChessPieceType {

    CHARIOT(
            List.of(new ChessPosition(0, 0), new ChessPosition(0, 8)),
            List.of(new ChessPosition(9, 0), new ChessPosition(9, 8)),
            Chariot::new
    ),
    HORSE(
            List.of(new ChessPosition(0, 1), new ChessPosition(0, 7)),
            List.of(new ChessPosition(9, 1), new ChessPosition(9, 7)),
            Horse::new
    ),
    ELEPHANT(
            List.of(new ChessPosition(0, 2), new ChessPosition(0, 6)),
            List.of(new ChessPosition(9, 2), new ChessPosition(9, 6)),
            Elephant::new
    ),
    CANNON(
            List.of(new ChessPosition(2, 1), new ChessPosition(2, 7)),
            List.of(new ChessPosition(7, 1), new ChessPosition(7, 7)),
            Chariot::new
    ),
    GUARD(
            List.of(new ChessPosition(0, 3), new ChessPosition(0, 5)),
            List.of(new ChessPosition(9, 3), new ChessPosition(9, 5)),
            Guard::new
    ),
    PAWN(
            List.of(new ChessPosition(3, 0), new ChessPosition(3, 2), new ChessPosition(3, 4), new ChessPosition(3, 6), new ChessPosition(3, 8)),
            List.of(new ChessPosition(6, 0), new ChessPosition(6, 2), new ChessPosition(6, 4), new ChessPosition(6, 6), new ChessPosition(6, 8)),
            Pawn::new
    ),
    KING(
            List.of(new ChessPosition(1, 4)),
            List.of(new ChessPosition(8, 4)),
            King::new
    );

    private final List<ChessPosition> redPosition;
    private final List<ChessPosition> bluePosition;
    private final Function<ChessTeam,ChessPiece> generator;

    ChessPieceType(final List<ChessPosition> redPosition, final List<ChessPosition> bluePosition,
                   final Function<ChessTeam, ChessPiece> generator) {
        this.redPosition = redPosition;
        this.bluePosition = bluePosition;
        this.generator = generator;
    }

    public List<ChessPosition> getRedPosition() {
        return redPosition;
    }

    public List<ChessPosition> getBluePosition() {
        return bluePosition;
    }

    public ChessPiece generateChessPiece(ChessTeam team) {
        return this.generator.apply(team);
    }
}
