package domain.piece;

import static domain.piece.NumberState.NEGATIVE;
import static domain.piece.NumberState.POSITIVE;
import static domain.piece.NumberState.ZERO;
import static domain.piece.NumberState.findNumberState;

import domain.BoardLocation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Axis {

    POSITIVE_X(POSITIVE, ZERO) {
        @Override
        List<BoardLocation> createAllPath(BoardLocation current, int dx, int dy) {
            List<BoardLocation> path = new ArrayList<>();
            for (int i = 1; i < dx; i++) {
                path.add(current.moveX(i));
            }
            return path;
        }
    },
    POSITIVE_Y(ZERO, POSITIVE) {
        @Override
        List<BoardLocation> createAllPath(BoardLocation current, int dx, int dy) {
            List<BoardLocation> path = new ArrayList<>();
            for (int i = 1; i < dy; i++) {
                path.add(current.moveY(i));
            }
            return path;
        }
    },
    NEGATIVE_X(NEGATIVE, ZERO) {
        @Override
        List<BoardLocation> createAllPath(BoardLocation current, int dx, int dy) {
            List<BoardLocation> path = new ArrayList<>();
            for (int i = -1; i > dx; i--) {
                path.add(current.moveX(i));
            }
            return path;
        }
    },
    NEGATIVE_Y(ZERO, NEGATIVE) {
        @Override
        List<BoardLocation> createAllPath(BoardLocation current, int dx, int dy) {
            List<BoardLocation> path = new ArrayList<>();
            for (int i = -1; i > dy; i--) {
                path.add(current.moveY(i));
            }
            return path;
        }
    };

    private final NumberState xState;
    private final NumberState yState;

    Axis(NumberState xState, NumberState yState) {
        this.xState = xState;
        this.yState = yState;
    }

    public static Axis findQuadrant(int dx, int dy) {
        NumberState xState = findNumberState(dx);
        NumberState yState = findNumberState(dy);
        return Arrays.stream(Axis.values())
                .filter(axis -> axis.xState == xState && axis.yState == yState)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 좌표 축을 찾지 못했습니다."));
    }

    abstract List<BoardLocation> createAllPath(BoardLocation current, int dx, int dy);
}
