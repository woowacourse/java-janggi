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
            MovementType.PALACE_CONSIDERATE,
            PathObstacleRule.MUST_JUMP_EXACTLY_ONE_OBSTACLE,
            7
    ),
    CHARIOT(
            new Movements(
                    new Movement(UP),
                    new Movement(DOWN),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MovementType.PALACE_CONSIDERATE,
            PathObstacleRule.CANNOT_PASS_OBSTACLES,
            13
    ),
    CHO_SOLDIER(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MovementType.PALACE_CONSIDERATE,
            PathObstacleRule.CANNOT_PASS_OBSTACLES,
            2
    ),
    HAN_SOLDIER(
            new Movements(
                    new Movement(DOWN),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MovementType.PALACE_CONSIDERATE,
            PathObstacleRule.CANNOT_PASS_OBSTACLES,
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
            MovementType.BASIC,
            PathObstacleRule.CANNOT_PASS_OBSTACLES,
            3
    ),
    GUARD(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT),
                    new Movement(DOWN)
            ),
            MovementType.PALACE_CONSIDERATE,
            PathObstacleRule.CANNOT_PASS_OBSTACLES,
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
            MovementType.BASIC,
            PathObstacleRule.CANNOT_PASS_OBSTACLES,
            5
    ),
    KING(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT),
                    new Movement(DOWN)
            ),
            MovementType.PALACE_CONSIDERATE,
            PathObstacleRule.CANNOT_PASS_OBSTACLES,
            0
    );

    private final Movements possibleMovements;
    private final MovementType movementType;
    private final PathObstacleRule pathObstacleRule;
    private final int score;

    Piece(final Movements possibleMovements, final MovementType movementType,
          final PathObstacleRule pathObstacleRule,
          final int score) {
        this.possibleMovements = possibleMovements;
        this.movementType = movementType;
        this.pathObstacleRule = pathObstacleRule;
        this.score = score;
    }

    public static Piece from(final String name, final Team team) {
        if (isSoldier(name)) {
            return findSoldierByTeam(team);
        }
        return findPiece(name);
    }

    private static boolean isSoldier(final String name) {
        return CHO_SOLDIER.name().contains(name);
    }

    private static Piece findSoldierByTeam(final Team team) {
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

    public void validateMovement(final Position from, final Position to, final Board board) {
        validatePalaceMovementRules(from, to);
        validatePathObstacles(from, to, board);
    }

    private void validatePalaceMovementRules(final Position from, final Position to) {
        if (isRestrictedByPalace()) {
            from.validateIsInPalace(to);
        }
    }

    private void validatePathObstacles(final Position from, final Position to, final Board board) {
        final Movement movement = movementType.findValidMovement(this, from, to);
        final Path path = movement.makePath(from, to);
        pathObstacleRule.validatePathObstacles(path, board);
    }

    public boolean canJumpObstacle() {
        return this.pathObstacleRule == PathObstacleRule.MUST_JUMP_EXACTLY_ONE_OBSTACLE;
    }

    public boolean canMoveMultipleSteps() {
        return this == CHARIOT || this == CANNON;
    }

    public boolean isKing() {
        return this == Piece.KING;
    }

    public boolean isSoldier() {
        return this == CHO_SOLDIER || this == HAN_SOLDIER;
    }

    private boolean isRestrictedByPalace() {
        return this == KING || this == GUARD;
    }

    public Movements getPossibleMovements() {
        return possibleMovements;
    }

    public int getScore() {
        return score;
    }
}
