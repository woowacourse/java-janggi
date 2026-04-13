package domain.strategy;

import domain.game.Side;
import java.util.List;

public enum Direction {
    N(0, 1),
    S(0, -1),
    E(1, 0),
    W(-1, 0),
    NE(1, 1),
    NW(-1, 1),
    SE(1, -1),
    SW(-1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static List<Direction> linear() {
        return List.of(N, S, E, W);
    }

    public static List<List<Direction>> horseSequences() {
        return List.of(
                List.of(N, NW), List.of(N, NE),
                List.of(S, SW), List.of(S, SE),
                List.of(E, NE), List.of(E, SE),
                List.of(W, NW), List.of(W, SW)
        );
    }

    public static List<List<Direction>> elephantSequences() {
        return List.of(
                List.of(N, NW, NW), List.of(N, NE, NE),
                List.of(S, SW, SW), List.of(S, SE, SE),
                List.of(E, NE, NE), List.of(E, SE, SE),
                List.of(W, NW, NW), List.of(W, SW, SW)
        );
    }

    public static List<Direction> soldier(Side side) {
        return List.of(E, W, side.soldierForward());
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
