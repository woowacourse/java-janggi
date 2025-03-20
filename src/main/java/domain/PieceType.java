package domain;

import static domain.Offset.DOWN;
import static domain.Offset.LEFT;
import static domain.Offset.LEFT_DOWN;
import static domain.Offset.LEFT_UP;
import static domain.Offset.RIGHT;
import static domain.Offset.RIGHT_DOWN;
import static domain.Offset.RIGHT_UP;
import static domain.Offset.UP;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum PieceType {
    CANNON("포", Map.of(
            Team.GREEN, List.of(new BoardPosition(1, 2), new BoardPosition(7, 2)),
            Team.RED, List.of(new BoardPosition(1, 7), new BoardPosition(7, 7))
    ), Map.ofEntries(
            Map.entry(new Offset(2, 0),
                    List.of(RIGHT, RIGHT)),
            Map.entry(new Offset(3, 0),
                    List.of(RIGHT, RIGHT, RIGHT)),
            Map.entry(new Offset(4, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT)),
            Map.entry(new Offset(5, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT)),
            Map.entry(new Offset(6, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT, RIGHT)),
            Map.entry(new Offset(7, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT, RIGHT,
                            RIGHT)),
            Map.entry(new Offset(8, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT)),
            Map.entry(new Offset(-2, 0),
                    List.of(LEFT, LEFT)),
            Map.entry(new Offset(-3, 0),
                    List.of(LEFT, LEFT, LEFT)),
            Map.entry(new Offset(-4, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT)),
            Map.entry(new Offset(-5, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT, LEFT)),
            Map.entry(new Offset(-6, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT, LEFT, LEFT)),
            Map.entry(new Offset(-7, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT, LEFT, LEFT,
                            LEFT)),
            Map.entry(new Offset(-8, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT, LEFT, LEFT,
                            LEFT, LEFT)),
            Map.entry(new Offset(0, 2),
                    List.of(UP, UP)),
            Map.entry(new Offset(0, 3),
                    List.of(UP, UP, UP)),
            Map.entry(new Offset(0, 4),
                    List.of(UP, UP, UP,
                            UP)),
            Map.entry(new Offset(0, 5),
                    List.of(UP, UP, UP,
                            UP, UP)),
            Map.entry(new Offset(0, 6),
                    List.of(UP, UP, UP,
                            UP, UP, UP)),
            Map.entry(new Offset(0, 7),
                    List.of(UP, UP, UP,
                            UP, UP, UP,
                            UP)),
            Map.entry(new Offset(0, 8),
                    List.of(UP, UP, UP,
                            UP, UP, UP,
                            UP, UP)),
            Map.entry(new Offset(0, 9),
                    List.of(UP, UP, UP,
                            UP, UP, UP,
                            UP, UP, UP)),
            Map.entry(new Offset(0, -2),
                    List.of(DOWN, DOWN)),
            Map.entry(new Offset(0, -3),
                    List.of(DOWN, DOWN, DOWN)),
            Map.entry(new Offset(0, -4),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN)),
            Map.entry(new Offset(0, -5),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN)),
            Map.entry(new Offset(0, -6),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN)),
            Map.entry(new Offset(0, -7),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN,
                            DOWN)),
            Map.entry(new Offset(0, -8),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN,
                            DOWN, DOWN)),
            Map.entry(new Offset(0, -9),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN))
    ), 1),
    CHARIOT("차", Map.of(
            Team.GREEN, List.of(new BoardPosition(0, 0), new BoardPosition(8, 0)),
            Team.RED, List.of(new BoardPosition(0, 9), new BoardPosition(8, 9))
    ), Map.ofEntries(
            Map.entry(RIGHT, List.of(RIGHT)),
            Map.entry(new Offset(2, 0),
                    List.of(RIGHT, RIGHT)),
            Map.entry(new Offset(3, 0),
                    List.of(RIGHT, RIGHT, RIGHT)),
            Map.entry(new Offset(4, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT)),
            Map.entry(new Offset(5, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT)),
            Map.entry(new Offset(6, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT, RIGHT)),
            Map.entry(new Offset(7, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT, RIGHT,
                            RIGHT)),
            Map.entry(new Offset(8, 0),
                    List.of(RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT, RIGHT,
                            RIGHT, RIGHT)),
            Map.entry(LEFT, List.of(LEFT)),
            Map.entry(new Offset(-2, 0),
                    List.of(LEFT, LEFT)),
            Map.entry(new Offset(-3, 0),
                    List.of(LEFT, LEFT, LEFT)),
            Map.entry(new Offset(-4, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT)),
            Map.entry(new Offset(-5, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT, LEFT)),
            Map.entry(new Offset(-6, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT, LEFT, LEFT)),
            Map.entry(new Offset(-7, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT, LEFT, LEFT,
                            LEFT)),
            Map.entry(new Offset(-8, 0),
                    List.of(LEFT, LEFT, LEFT,
                            LEFT, LEFT, LEFT,
                            LEFT, LEFT)),
            Map.entry(UP, List.of(UP)),
            Map.entry(new Offset(0, 2),
                    List.of(UP, UP)),
            Map.entry(new Offset(0, 3),
                    List.of(UP, UP, UP)),
            Map.entry(new Offset(0, 4),
                    List.of(UP, UP, UP,
                            UP)),
            Map.entry(new Offset(0, 5),
                    List.of(UP, UP, UP,
                            UP, UP)),
            Map.entry(new Offset(0, 6),
                    List.of(UP, UP, UP,
                            UP, UP, UP)),
            Map.entry(new Offset(0, 7),
                    List.of(UP, UP, UP,
                            UP, UP, UP,
                            UP)),
            Map.entry(new Offset(0, 8),
                    List.of(UP, UP, UP,
                            UP, UP, UP,
                            UP, UP)),
            Map.entry(new Offset(0, 9),
                    List.of(UP, UP, UP,
                            UP, UP, UP,
                            UP, UP, UP)),
            Map.entry(DOWN, List.of(DOWN)),
            Map.entry(new Offset(0, -2),
                    List.of(DOWN, DOWN)),
            Map.entry(new Offset(0, -3),
                    List.of(DOWN, DOWN, DOWN)),
            Map.entry(new Offset(0, -4),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN)),
            Map.entry(new Offset(0, -5),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN)),
            Map.entry(new Offset(0, -6),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN)),
            Map.entry(new Offset(0, -7),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN,
                            DOWN)),
            Map.entry(new Offset(0, -8),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN,
                            DOWN, DOWN)),
            Map.entry(new Offset(0, -9),
                    List.of(DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN,
                            DOWN, DOWN, DOWN))
    ), 0),
    ELEPHANT("상", Map.of(
            Team.GREEN, List.of(new BoardPosition(1, 0), new BoardPosition(7, 0)),
            Team.RED, List.of(new BoardPosition(1, 9), new BoardPosition(7, 9))
    ), Map.of(
            new Offset(3, 2),
            List.of(RIGHT, RIGHT_UP, RIGHT_UP),
            new Offset(3, -2),
            List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
            new Offset(-3, 2),
            List.of(LEFT, LEFT_UP, LEFT_UP),
            new Offset(-3, -2),
            List.of(LEFT, LEFT_DOWN, LEFT_DOWN),
            new Offset(2, 3),
            List.of(UP, RIGHT_UP, RIGHT_UP),
            new Offset(2, -3),
            List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN),
            new Offset(-2, 3),
            List.of(UP, LEFT_UP, LEFT_UP),
            new Offset(-2, -3),
            List.of(DOWN, LEFT_DOWN, LEFT_DOWN)
    ), 0),
    GENERAL("왕", Map.of(
            Team.GREEN, List.of(new BoardPosition(4, 1)),
            Team.RED, List.of(new BoardPosition(4, 8))
    ), Map.of(
            RIGHT, List.of(RIGHT),
            LEFT, List.of(LEFT),
            UP, List.of(UP),
            DOWN, List.of(DOWN)
    ), 0),
    GUARD("사", Map.of(
            Team.GREEN, List.of(new BoardPosition(3, 0), new BoardPosition(5, 0)),
            Team.RED, List.of(new BoardPosition(3, 9), new BoardPosition(5, 9))
    ), Map.of(
            RIGHT, List.of(RIGHT),
            LEFT, List.of(LEFT),
            UP, List.of(UP),
            DOWN, List.of(DOWN)
    ), 0),
    HORSE("마", Map.of(
            Team.GREEN, List.of(new BoardPosition(2, 0), new BoardPosition(6, 0)),
            Team.RED, List.of(new BoardPosition(2, 9), new BoardPosition(6, 9))
    ), Map.of(
            new Offset(2, 1), List.of(RIGHT, RIGHT_UP),
            new Offset(2, -1), List.of(RIGHT, RIGHT_DOWN),
            new Offset(-2, 1), List.of(LEFT, LEFT_UP),
            new Offset(-2, -1), List.of(LEFT, LEFT_DOWN),
            new Offset(1, 2), List.of(UP, RIGHT_UP),
            new Offset(1, -2), List.of(DOWN, RIGHT_DOWN),
            new Offset(-1, 2), List.of(UP, LEFT_UP),
            new Offset(-1, -2), List.of(DOWN, LEFT_DOWN)
    ), 0),
    ZZU("쭈", Map.of(
            Team.GREEN,
            List.of(new BoardPosition(0, 3), new BoardPosition(2, 3),
                    new BoardPosition(4, 3),
                    new BoardPosition(6, 3), new BoardPosition(8, 3)),
            Team.RED,
            List.of(new BoardPosition(0, 6), new BoardPosition(2, 6),
                    new BoardPosition(4, 6),
                    new BoardPosition(6, 6), new BoardPosition(8, 6))
    ), Map.of(
            RIGHT, List.of(RIGHT),
            LEFT, List.of(LEFT),
            UP, List.of(UP),
            DOWN, List.of(DOWN)
    ), 0);

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
