package janggi.move;

import static janggi.move.Direction.DOWN;
import static janggi.move.Direction.DOWN_LEFT;
import static janggi.move.Direction.DOWN_RIGHT;
import static janggi.move.Direction.LEFT;
import static janggi.move.Direction.RIGHT;
import static janggi.move.Direction.UP;
import static janggi.move.Direction.UP_LEFT;
import static janggi.move.Direction.UP_RIGHT;

import janggi.piece.board.Board;
import janggi.piece.players.Team;
import janggi.position.Position;
import java.util.Arrays;

public enum Piece {

    CANNON(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT),
                    new Movement(DOWN)
            ),
            MoveStrategy.EDGE,
            ObstacleStrategy.JUMPING,
            7
    ),
    CHARIOT(
            new Movements(
                    new Movement(UP),
                    new Movement(DOWN),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MoveStrategy.EDGE,
            ObstacleStrategy.BLOCK,
            13
    ),
    CHO_SOLDIER(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MoveStrategy.EDGE,
            ObstacleStrategy.BLOCK,
            2
    ),
    HAN_SOLDIER(
            new Movements(
                    new Movement(DOWN),
                    new Movement(RIGHT),
                    new Movement(LEFT)
            ),
            MoveStrategy.EDGE,
            ObstacleStrategy.BLOCK,
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
            MoveStrategy.RELATIVE,
            ObstacleStrategy.BLOCK,
            3
    ),
    GUARD(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT),
                    new Movement(DOWN)
            ),
            MoveStrategy.EDGE,
            ObstacleStrategy.BLOCK,
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
            MoveStrategy.RELATIVE,
            ObstacleStrategy.BLOCK,
            5
    ),
    KING(
            new Movements(
                    new Movement(UP),
                    new Movement(RIGHT),
                    new Movement(LEFT),
                    new Movement(DOWN)
            ),
            MoveStrategy.EDGE,
            ObstacleStrategy.BLOCK,
            0
    );


    private final Movements movements;
    private final MoveStrategy moveStrategy;
    private final ObstacleStrategy obstacleStrategy;
    private final int score;
    private PathValidator pathValidator;

    Piece(
            final Movements movements,
            final MoveStrategy moveStrategy,
            final ObstacleStrategy obstacleStrategy,
            final int score
    ) {
        this.movements = movements;
        this.moveStrategy = moveStrategy;
        this.obstacleStrategy = obstacleStrategy;
        this.score = score;
    }

    public static Piece from(final String name, final Team team) {
        if (isSoldier(name)) {
            return getSoldier(team);
        }
        return findPieceType(name);
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

    private static Piece findPieceType(final String name) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] PieceType을 찾을 수 없습니다."));
    }

    public void move(final Position from, final Position to, final Board board) {
        final Movement movement = moveStrategy.move(from, to, this);
        getPathValidator().validatePath(from, to, board, movement);
    }

    private PathValidator getPathValidator() {
        if (pathValidator == null) {
            return new PathValidator(this, obstacleStrategy);
        }
        return pathValidator;
    }

    public boolean doesLiveInPalace() {
        return this == KING || this == GUARD;
    }

    public boolean isObstacleJumping() {
        return this.obstacleStrategy == ObstacleStrategy.JUMPING;
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
