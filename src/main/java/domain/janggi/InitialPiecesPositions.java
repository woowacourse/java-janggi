package domain.janggi;

import domain.board.BoardPosition;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public enum InitialPiecesPositions {
    ZZU(PieceType.ZZU, Map.of(
            Team.GREEN,
            List.of(new BoardPosition(0, 3), new BoardPosition(2, 3), new BoardPosition(4, 3),
                    new BoardPosition(6, 3), new BoardPosition(8, 3)),
            Team.RED,
            List.of(new BoardPosition(0, 6), new BoardPosition(2, 6), new BoardPosition(4, 6),
                    new BoardPosition(6, 6), new BoardPosition(8, 6))
    )),
    CHARIOT(PieceType.CHARIOT, Map.of(
            Team.GREEN, List.of(new BoardPosition(0, 0), new BoardPosition(8, 0)),
            Team.RED, List.of(new BoardPosition(0, 9), new BoardPosition(8, 9))
    )),
    HORSE(PieceType.HORSE, Map.of(
            Team.GREEN, List.of(new BoardPosition(2, 0), new BoardPosition(6, 0)),
            Team.RED, List.of(new BoardPosition(2, 9), new BoardPosition(6, 9))
    )),
    ELEPHANT(PieceType.ELEPHANT, Map.of(
            Team.GREEN, List.of(new BoardPosition(1, 0), new BoardPosition(7, 0)),
            Team.RED, List.of(new BoardPosition(1, 9), new BoardPosition(7, 9))
    )),
    CANNON(PieceType.CANNON, Map.of(
            Team.GREEN, List.of(new BoardPosition(1, 2), new BoardPosition(7, 2)),
            Team.RED, List.of(new BoardPosition(1, 7), new BoardPosition(7, 7))
    )),
    GENERAL(PieceType.GENERAL, Map.of(
            Team.GREEN, List.of(new BoardPosition(4, 1)),
            Team.RED, List.of(new BoardPosition(4, 8))
    )),
    GUARD(PieceType.GUARD, Map.of(
            Team.GREEN, List.of(new BoardPosition(3, 0), new BoardPosition(5, 0)),
            Team.RED, List.of(new BoardPosition(3, 9), new BoardPosition(5, 9))
    ));

    private final PieceType pieceType;
    private final Map<Team, List<BoardPosition>> initialPosition;

    InitialPiecesPositions(
            final PieceType pieceType,
            final Map<Team, List<BoardPosition>> initialPosition
    ) {
        this.pieceType = pieceType;
        this.initialPosition = initialPosition;
    }

    public List<BoardPosition> getBoardPosition(final Team team) {
        return initialPosition.get(team);
    }

    public Piece generatePiece(final Team team) {
        return pieceType.generate(team);
    }
}
