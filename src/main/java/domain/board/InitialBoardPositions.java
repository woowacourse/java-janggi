package domain.board;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Jju;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum InitialBoardPositions {
    CANNON(Cannon::new, Map.of(
        Team.GREEN, List.of(new BoardPosition(1, 2), new BoardPosition(7, 2)),
        Team.RED, List.of(new BoardPosition(1, 7), new BoardPosition(7, 7))
    )),
    CHARIOT(Chariot::new, Map.of(
        Team.GREEN, List.of(new BoardPosition(0, 0), new BoardPosition(8, 0)),
        Team.RED, List.of(new BoardPosition(0, 9), new BoardPosition(8, 9))
    )),
    ELEPHANT(Elephant::new, Map.of(
        Team.GREEN, List.of(new BoardPosition(1, 0), new BoardPosition(7, 0)),
        Team.RED, List.of(new BoardPosition(1, 9), new BoardPosition(7, 9))
    )),
    GENERAL(General::new, Map.of(
        Team.GREEN, List.of(new BoardPosition(4, 1)),
        Team.RED, List.of(new BoardPosition(4, 8))
    )),
    GUARD(Guard::new, Map.of(
        Team.GREEN, List.of(new BoardPosition(3, 0), new BoardPosition(5, 0)),
        Team.RED, List.of(new BoardPosition(3, 9), new BoardPosition(5, 9))
    )),
    HORSE(Horse::new, Map.of(
        Team.GREEN, List.of(new BoardPosition(2, 0), new BoardPosition(6, 0)),
        Team.RED, List.of(new BoardPosition(2, 9), new BoardPosition(6, 9))
    )),
    JJU(Jju::new, Map.of(
        Team.GREEN, List.of(
            new BoardPosition(0, 3), new BoardPosition(2, 3),
            new BoardPosition(4, 3), new BoardPosition(6, 3), new BoardPosition(8, 3)
        ),
        Team.RED, List.of(
            new BoardPosition(0, 6), new BoardPosition(2, 6),
            new BoardPosition(4, 6), new BoardPosition(6, 6), new BoardPosition(8, 6)
        )
    ));

    private final Function<Team, Piece> pieceCreator;
    private final Map<Team, List<BoardPosition>> initialPosition;

    InitialBoardPositions(
        final Function<Team, Piece> pieceCreator,
        final Map<Team, List<BoardPosition>> initialPosition
    ) {
        this.pieceCreator = pieceCreator;
        this.initialPosition = initialPosition;
    }

    public Piece createPiece(final Team team) {
        return pieceCreator.apply(team);
    }

    public Map<Team, List<BoardPosition>> getInitialPosition() {
        return initialPosition;
    }
}
