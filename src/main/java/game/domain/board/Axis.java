package game.domain.board;

import static game.util.NumberState.NEGATIVE;
import static game.util.NumberState.POSITIVE;
import static game.util.NumberState.ZERO;
import static game.util.NumberState.findNumberState;

import game.util.NumberState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Axis {

    POSITIVE_X(POSITIVE, ZERO) {
        @Override
        public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector) {
            List<BoardLocation> path = new ArrayList<>();
            for (int i = 1; i < boardVector.dx(); i++) {
                path.add(current.moveX(i));
            }
            return path;
        }
    },
    POSITIVE_Y(ZERO, POSITIVE) {
        @Override
        public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector) {
            List<BoardLocation> path = new ArrayList<>();
            for (int i = 1; i < boardVector.dy(); i++) {
                path.add(current.moveY(i));
            }
            return path;
        }
    },
    NEGATIVE_X(NEGATIVE, ZERO) {
        @Override
        public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector) {
            List<BoardLocation> path = new ArrayList<>();
            for (int i = -1; i > boardVector.dx(); i--) {
                path.add(current.moveX(i));
            }
            return path;
        }
    },
    NEGATIVE_Y(ZERO, NEGATIVE) {
        @Override
        public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector) {
            List<BoardLocation> path = new ArrayList<>();
            for (int i = -1; i > boardVector.dy(); i--) {
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

    public static Axis findQuadrant(BoardVector boardVector) {
        NumberState xState = findNumberState(boardVector.dx());
        NumberState yState = findNumberState(boardVector.dy());
        return Arrays.stream(Axis.values())
                .filter(axis -> axis.xState == xState && axis.yState == yState)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 좌표 축을 찾지 못했습니다."));
    }

    abstract public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector);
}
