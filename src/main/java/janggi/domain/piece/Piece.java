package janggi.domain.piece;

import static janggi.domain.piece.direction.Direction.DOWN;
import static janggi.domain.piece.direction.Direction.DOWN_LEFT;
import static janggi.domain.piece.direction.Direction.DOWN_RIGHT;
import static janggi.domain.piece.direction.Direction.LEFT;
import static janggi.domain.piece.direction.Direction.RIGHT;
import static janggi.domain.piece.direction.Direction.UP;
import static janggi.domain.piece.direction.Direction.UP_LEFT;
import static janggi.domain.piece.direction.Direction.UP_RIGHT;

import janggi.domain.board.Board;
import janggi.domain.piece.direction.Movement;
import janggi.domain.piece.direction.Movements;
import janggi.domain.piece.position.Path;
import janggi.domain.piece.position.Position;
import janggi.domain.players.Team;
import java.util.Arrays;

public enum Piece {

    CANNON(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT),
                    new Movement(DOWN)
            ),
            MovementType.PALACE_AWARE,
            ObstacleTraversalRule.JUMP_ONE_OBSTACLE,
            7
    ),
    CHARIOT(
            new Movements(
                    new Movement(UP),
                    new Movement(DOWN),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MovementType.PALACE_AWARE,
            ObstacleTraversalRule.BLOCK,
            13
    ),
    CHO_SOLDIER(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MovementType.PALACE_AWARE,
            ObstacleTraversalRule.BLOCK,
            2
    ),
    HAN_SOLDIER(
            new Movements(
                    new Movement(DOWN),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MovementType.PALACE_AWARE,
            ObstacleTraversalRule.BLOCK,
            2
    ),
    ELEPHANT(
            new Movements(
                    new Movement(DOWN, DOWN_RIGHT, DOWN_RIGHT),
                    new Movement(DOWN, DOWN_LEFT, DOWN_LEFT),
                    new Movement(UP, UP_RIGHT, UP_RIGHT),
                    new Movement(UP, UP_LEFT, UP_LEFT),
                    new Movement(RIGHT, DOWN_RIGHT, DOWN_RIGHT),
                    new Movement(LEFT, DOWN_LEFT, DOWN_LEFT),
                    new Movement(RIGHT, UP_RIGHT, UP_RIGHT),
                    new Movement(LEFT, UP_LEFT, UP_LEFT)
            ),
            MovementType.STANDARD,
            ObstacleTraversalRule.BLOCK,
            3
    ),
    GUARD(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT),
                    new Movement(DOWN)
            ),
            MovementType.PALACE_AWARE,
            ObstacleTraversalRule.BLOCK,
            3
    ),
    HORSE(
            new Movements(
                    new Movement(DOWN, DOWN_RIGHT),
                    new Movement(DOWN, DOWN_LEFT),
                    new Movement(UP, UP_RIGHT),
                    new Movement(UP, UP_LEFT),
                    new Movement(RIGHT, DOWN_RIGHT),
                    new Movement(LEFT, DOWN_LEFT),
                    new Movement(RIGHT, UP_RIGHT),
                    new Movement(LEFT, UP_LEFT)
            ),
            MovementType.STANDARD,
            ObstacleTraversalRule.BLOCK,
            5
    ),
    KING(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT),
                    new Movement(DOWN)
            ),
            MovementType.PALACE_AWARE,
            ObstacleTraversalRule.BLOCK,
            0
    );

    private final Movements movements;
    private final MovementType movementType;
    private final ObstacleTraversalRule obstacleTraversalRule;
    private final int score;

    Piece(
            final Movements movements,
            final MovementType movementType,
            final ObstacleTraversalRule obstacleTraversalRule,
            final int score
    ) {
        this.movements = movements;
        this.movementType = movementType;
        this.obstacleTraversalRule = obstacleTraversalRule;
        this.score = score;
    }

    public static Piece from(final String name, final Team team) {
        if (isSoldier(name)) {
            return getSoldier(team);
        }
        return findPiece(name);
    }

    private static boolean isSoldier(final String name) {
        return CHO_SOLDIER.name().contains(name);
    }

    private static Piece getSoldier(final Team team) {
        if (team == Team.CHO) {
            return CHO_SOLDIER;
        }
        return HAN_SOLDIER;
    }

    private static Piece findPiece(final String name) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 기물을 찾을 수 없습니다."));
    }

    public void validateMove(final Position from, final Position to, final Board board) {
        final Movement movement = movementType.determineMovement(this, from, to);
        validatePath(from, to, doesLiveInPalace(), board, movement);
    }

    private void validatePath(final Position from, final Position to, final boolean doesLiveInPalace, final Board board,
                              final Movement movement) {
        if (doesLiveInPalace) {
            from.validateIsInPalace(to);
        }
        final Path path = movement.makePath(from, to);
        obstacleTraversalRule.validatePathObstacles(path, board);
    }

    public boolean doesLiveInPalace() {
        return this == KING || this == GUARD;
    }

    public boolean isObstacleJumping() {
        return this.obstacleTraversalRule == ObstacleTraversalRule.JUMP_ONE_OBSTACLE;
    }

    public boolean canMoveIterable() {
        return this == CHARIOT || this == CANNON;
    }

    public boolean isKing() {
        return this == Piece.KING;
    }

    public boolean isSoldier() {
        return this == CHO_SOLDIER || this == HAN_SOLDIER;
    }

    public Movements getMovements() {
        return movements;
    }

    public int getScore() {
        return score;
    }
}
