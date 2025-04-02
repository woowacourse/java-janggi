package janggi.direction;

import static janggi.direction.Direction.DOWN;
import static janggi.direction.Direction.DOWN_LEFT;
import static janggi.direction.Direction.DOWN_RIGHT;
import static janggi.direction.Direction.LEFT;
import static janggi.direction.Direction.RIGHT;
import static janggi.direction.Direction.UP;
import static janggi.direction.Direction.UP_LEFT;
import static janggi.direction.Direction.UP_RIGHT;

import janggi.piece.players.Team;
import java.util.Arrays;
import java.util.List;

public enum PieceType {

    CANNON(new Movements(List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT),
            new Movement(DOWN)
    )), 7),
    CHARIOT(new Movements(List.of(
            new Movement(UP),
            new Movement(DOWN),
            new Movement(RIGHT),
            new Movement(LEFT)
    )), 13),
    CHO_SOLDIER(new Movements(List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT)
    )), 2),
    HAN_SOLDIER(new Movements(List.of(
            new Movement(DOWN),
            new Movement(RIGHT),
            new Movement(LEFT)
    )), 2),
    ELEPHANT(new Movements(List.of(
            new Movement(DOWN, DOWN_RIGHT, DOWN_RIGHT),
            new Movement(DOWN, DOWN_LEFT, DOWN_LEFT),
            new Movement(UP, UP_RIGHT, UP_RIGHT),
            new Movement(UP, UP_LEFT, UP_LEFT),
            new Movement(RIGHT, DOWN_RIGHT, DOWN_RIGHT),
            new Movement(LEFT, DOWN_LEFT, DOWN_LEFT),
            new Movement(RIGHT, UP_RIGHT, UP_RIGHT),
            new Movement(LEFT, UP_LEFT, UP_LEFT)
    )), 3),
    GUARD(new Movements(List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT),
            new Movement(DOWN)
    )), 3),
    HORSE(new Movements(List.of(
            new Movement(DOWN, DOWN_RIGHT),
            new Movement(DOWN, DOWN_LEFT),
            new Movement(UP, UP_RIGHT),
            new Movement(UP, UP_LEFT),
            new Movement(RIGHT, DOWN_RIGHT),
            new Movement(LEFT, DOWN_LEFT),
            new Movement(RIGHT, UP_RIGHT),
            new Movement(LEFT, UP_LEFT)
    )), 5),
    KING(new Movements(List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT),
            new Movement(DOWN)
    )), 0);

    private static final String SOLDIER = "SOLDIER";

    private final Movements movements;
    private final int score;

    PieceType(final Movements movements, final int score) {
        this.movements = movements;
        this.score = score;
    }

    public static PieceType from(final String name, final Team team) {
        if (name.equals(SOLDIER)) {
            if (team == Team.CHO) {
                return CHO_SOLDIER;
            }
            return HAN_SOLDIER;
        }
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] PieceType을 찾을 수 없습니다."));
    }

    public boolean doesLiveInPalace() {
        return this == KING || this == GUARD;
    }

    public boolean isEdgeMove() {
        return !(this == PieceType.HORSE || this == PieceType.ELEPHANT);
    }

    public boolean isKing() {
        return this == PieceType.KING;
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
