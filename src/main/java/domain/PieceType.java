package domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum PieceType {
    CANNON("포", Map.of(
        Team.GREEN, List.of(new Position(1, 2), new Position(7, 2)),
        Team.RED, List.of(new Position(1, 7), new Position(7, 7))
    ), Map.ofEntries(
        Map.entry(new Offset(2, 0),
            List.of(new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(3, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(4, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0))),
        Map.entry(new Offset(5, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(6, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(7, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0))),
        Map.entry(new Offset(8, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(-2, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(-3, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(-4, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0))),
        Map.entry(new Offset(-5, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(-6, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(-7, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0))),
        Map.entry(new Offset(-8, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(0, 2),
            List.of(new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 3),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 4),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1))),
        Map.entry(new Offset(0, 5),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 6),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 7),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1))),
        Map.entry(new Offset(0, 8),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 9),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, -2),
            List.of(new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -3),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -4),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1))),
        Map.entry(new Offset(0, -5),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -6),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -7),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1))),
        Map.entry(new Offset(0, -8),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -9),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1)))
    ), 1),
    CHARIOT("차", Map.of(
        Team.GREEN, List.of(new Position(0, 0), new Position(8, 0)),
        Team.RED, List.of(new Position(0, 9), new Position(8, 9))
    ), Map.ofEntries(
        Map.entry(new Offset(1, 0), List.of(new Offset(1, 0))),
        Map.entry(new Offset(2, 0),
            List.of(new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(3, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(4, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0))),
        Map.entry(new Offset(5, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(6, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(7, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0))),
        Map.entry(new Offset(8, 0),
            List.of(new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0), new Offset(1, 0),
                new Offset(1, 0), new Offset(1, 0))),
        Map.entry(new Offset(-1, 0), List.of(new Offset(-1, 0))),
        Map.entry(new Offset(-2, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(-3, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(-4, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0))),
        Map.entry(new Offset(-5, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(-6, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(-7, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0))),
        Map.entry(new Offset(-8, 0),
            List.of(new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0), new Offset(-1, 0),
                new Offset(-1, 0), new Offset(-1, 0))),
        Map.entry(new Offset(0, 1), List.of(new Offset(0, 1))),
        Map.entry(new Offset(0, 2),
            List.of(new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 3),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 4),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1))),
        Map.entry(new Offset(0, 5),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 6),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 7),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1))),
        Map.entry(new Offset(0, 8),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, 9),
            List.of(new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1),
                new Offset(0, 1), new Offset(0, 1), new Offset(0, 1))),
        Map.entry(new Offset(0, -1), List.of(new Offset(0, -1))),
        Map.entry(new Offset(0, -2),
            List.of(new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -3),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -4),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1))),
        Map.entry(new Offset(0, -5),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -6),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -7),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1))),
        Map.entry(new Offset(0, -8),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1))),
        Map.entry(new Offset(0, -9),
            List.of(new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1),
                new Offset(0, -1), new Offset(0, -1), new Offset(0, -1)))
    ), 0),
    ELEPHANT("상", Map.of(
        Team.GREEN, List.of(new Position(1, 0), new Position(7, 0)),
        Team.RED, List.of(new Position(1, 9), new Position(7, 9))
    ), Map.of(
        new Offset(3, 2),
        List.of(new Offset(1, 0), new Offset(1, 1), new Offset(1, 1)),
        new Offset(3, -2),
        List.of(new Offset(1, 0), new Offset(1, -1), new Offset(1, -1)),
        new Offset(-3, 2),
        List.of(new Offset(-1, 0), new Offset(-1, 1), new Offset(-1, 1)),
        new Offset(-3, -2),
        List.of(new Offset(-1, 0), new Offset(-1, -1), new Offset(-1, -1)),
        new Offset(2, 3),
        List.of(new Offset(0, 1), new Offset(1, 1), new Offset(1, 1)),
        new Offset(2, -3),
        List.of(new Offset(0, -1), new Offset(1, -1), new Offset(1, -1)),
        new Offset(-2, 3),
        List.of(new Offset(0, 1), new Offset(-1, 1), new Offset(-1, 1)),
        new Offset(-2, -3),
        List.of(new Offset(0, -1), new Offset(-1, -1), new Offset(-1, -1))
    ), 0),
    GENERAL("왕", Map.of(
        Team.GREEN, List.of(new Position(4, 1)),
        Team.RED, List.of(new Position(4, 8))
    ), Map.of(
        new Offset(1, 0), List.of(new Offset(1, 0)),
        new Offset(-1, 0), List.of(new Offset(-1, 0)),
        new Offset(0, 1), List.of(new Offset(0, 1)),
        new Offset(0, -1), List.of(new Offset(0, -1))
    ), 0),
    GUARD("사", Map.of(
        Team.GREEN, List.of(new Position(3, 0), new Position(5, 0)),
        Team.RED, List.of(new Position(3, 9), new Position(5, 9))
    ), Map.of(
        new Offset(1, 0), List.of(new Offset(1, 0)),
        new Offset(-1, 0), List.of(new Offset(-1, 0)),
        new Offset(0, 1), List.of(new Offset(0, 1)),
        new Offset(0, -1), List.of(new Offset(0, -1))
    ), 0),
    HORSE("마", Map.of(
        Team.GREEN, List.of(new Position(2, 0), new Position(6, 0)),
        Team.RED, List.of(new Position(2, 9), new Position(6, 9))
    ), Map.of(
        new Offset(2, 1), List.of(new Offset(1, 0), new Offset(1, 1)),
        new Offset(2, -1), List.of(new Offset(1, 0), new Offset(1, -1)),
        new Offset(-2, 1), List.of(new Offset(-1, 0), new Offset(-1, 1)),
        new Offset(-2, -1), List.of(new Offset(-1, 0), new Offset(-1, -1)),
        new Offset(1, 2), List.of(new Offset(0, 1), new Offset(1, 1)),
        new Offset(1, -2), List.of(new Offset(0, -1), new Offset(1, -1)),
        new Offset(-1, 2), List.of(new Offset(0, 1), new Offset(-1, 1)),
        new Offset(-1, -2), List.of(new Offset(0, -1), new Offset(-1, -1))
    ), 0),
    쭈("쭈", Map.of(
        Team.GREEN,
        List.of(new Position(0, 3), new Position(2, 3), new Position(4, 3),
            new Position(6, 3), new Position(8, 3)),
        Team.RED,
        List.of(new Position(0, 6), new Position(2, 6), new Position(4, 6),
            new Position(6, 6), new Position(8, 6))
    ), Map.of(
        new Offset(1, 0), List.of(new Offset(1, 0)),
        new Offset(-1, 0), List.of(new Offset(-1, 0)),
        new Offset(0, 1), List.of(new Offset(0, 1)),
        new Offset(0, -1), List.of(new Offset(0, -1))
    ), 0);

    private final String title;
    private final Map<Team, List<Position>> initialPosition;
    private final Map<Offset, List<Offset>> movementRules;
    private final int allowObstacleCount;


    PieceType(
        final String title,
        final Map<Team, List<Position>> initialPosition,
        final Map<Offset, List<Offset>> movementRules,
        final int allowObstacleCount
    ) {
        this.title = title;
        this.initialPosition = initialPosition;
        this.movementRules = movementRules;
        this.allowObstacleCount = allowObstacleCount;
    }

    public Map<Team, List<Position>> getInitialPosition() {
        return initialPosition;
    }

    public Optional<List<Offset>> findMovementRule(
        final Offset offset,
        final Team team
    ) {
        List<Offset> movementRule = movementRules.get(offset);

        if (movementRule == null) {
            return Optional.empty();
        }

        if (this == PieceType.쭈) {
            return getRestrictedMove(team, movementRule);
        }

        return Optional.of(Collections.unmodifiableList(movementRule));
    }

    private Optional<List<Offset>> getRestrictedMove(
        final Team team,
        final List<Offset> movementRule
    ) {
        if (team == Team.RED && movementRule.contains(new Offset(0, 1))) {
            return Optional.empty();
        }

        if (team == Team.GREEN && movementRule.contains(new Offset(0, -1))) {
            return Optional.empty();
        }

        return Optional.of(movementRule);
    }

    public int getAllowObstacleCount() {
        return allowObstacleCount;
    }
}
