package domain.piece;

import static domain.board.Offset.DOWN;
import static domain.board.Offset.UP;

import domain.Team;
import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum PieceType {
    CANNON("포", Map.of(
            Team.GREEN, List.of(new BoardPosition(1, 2), new BoardPosition(7, 2)),
            Team.RED, List.of(new BoardPosition(1, 7), new BoardPosition(7, 7))
    ), null, 1),


    CHARIOT("차", Map.of(
            Team.GREEN, List.of(new BoardPosition(0, 0), new BoardPosition(8, 0)),
            Team.RED, List.of(new BoardPosition(0, 9), new BoardPosition(8, 9))
    ), null, 0),


    ELEPHANT("상", Map.of(
            Team.GREEN, List.of(new BoardPosition(1, 0), new BoardPosition(7, 0)),
            Team.RED, List.of(new BoardPosition(1, 9), new BoardPosition(7, 9))
    ), null, 0),


    GENERAL("왕", Map.of(
            Team.GREEN, List.of(new BoardPosition(4, 1)),
            Team.RED, List.of(new BoardPosition(4, 8))
    ), null, 0),


    GUARD("사", Map.of(
            Team.GREEN, List.of(new BoardPosition(3, 0), new BoardPosition(5, 0)),
            Team.RED, List.of(new BoardPosition(3, 9), new BoardPosition(5, 9))
    ), null, 0),


    HORSE("마", Map.of(
            Team.GREEN, List.of(new BoardPosition(2, 0), new BoardPosition(6, 0)),
            Team.RED, List.of(new BoardPosition(2, 9), new BoardPosition(6, 9))
    ), null, 0),


    ZZU("쭈", Map.of(
            Team.GREEN,
            List.of(new BoardPosition(0, 3), new BoardPosition(2, 3),
                    new BoardPosition(4, 3),
                    new BoardPosition(6, 3), new BoardPosition(8, 3)),
            Team.RED,
            List.of(new BoardPosition(0, 6), new BoardPosition(2, 6),
                    new BoardPosition(4, 6),
                    new BoardPosition(6, 6), new BoardPosition(8, 6))
    ), null, 0);

    private final String title;
    private final Map<Team, List<BoardPosition>> initialPosition;
    private final Map<Offset, List<Offset>> movementRules;
    private final int allowObstacleCount;


    PieceType(
            final String title,
            final Map<Team, List<BoardPosition>> initialPosition,
            final Map<Offset, List<Offset>> movementRules,
            final int allowObstacleCount
    ) {
        this.title = title;
        this.initialPosition = initialPosition;
        this.movementRules = movementRules;
        this.allowObstacleCount = allowObstacleCount;
    }

    public Optional<List<Offset>> findMovementRule(
            final Offset offset,
            final Team team
    ) {
        final List<Offset> movementRule = movementRules.get(offset);

        if (movementRule == null || checkRestrictedMove(team, movementRule)) {
            return Optional.empty();
        }

        return Optional.of(Collections.unmodifiableList(movementRule));
    }

    private boolean checkRestrictedMove(
            final Team team,
            final List<Offset> movementRule
    ) {
        if (this == PieceType.ZZU && team == Team.RED && movementRule.contains(UP)) {
            return true;
        }

        if (this == PieceType.ZZU && team == Team.GREEN && movementRule.contains(DOWN)) {
            return true;
        }

        return false;
    }

    public String getTitle() {
        return title;
    }

    public Map<Team, List<BoardPosition>> getInitialPosition() {
        return initialPosition;
    }

    public int getAllowObstacleCount() {
        return allowObstacleCount;
    }
}
