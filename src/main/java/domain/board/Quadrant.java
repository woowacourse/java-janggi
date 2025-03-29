package domain.board;

import static util.NumberState.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.NumberState;

public enum Quadrant {
    FIRST_QUADRANT(POSITIVE, POSITIVE) {
        @Override
        public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector) {
            List<BoardLocation> path = new ArrayList<>();
            int absDy = Math.abs(boardVector.dy());
            for (int i = 1; i < absDy; i++) {
                path.add(current.move(i, i));
            }
            return path;
        }
    },
    SECOND_QUADRANT(NEGATIVE, POSITIVE) {
        @Override
        public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector) {
            List<BoardLocation> path = new ArrayList<>();
            int absDy = Math.abs(boardVector.dy());

            for (int i = 1; i < absDy; i++) {
                path.add(current.move(-i, i));
            }
            return path;
        }
    },
    THIRD_QUADRANT(NEGATIVE, NEGATIVE) {
        @Override
        public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector) {
            List<BoardLocation> path = new ArrayList<>();
            int absDy = Math.abs(boardVector.dy());

            for (int i = 1; i < absDy; i++) {
                path.add(current.move(-i, -i));
            }
            return path;
        }
    },
    FOURTH_QUADRANT(POSITIVE, NEGATIVE) {
        @Override
        public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector) {
            List<BoardLocation> path = new ArrayList<>();
            int absDy = Math.abs(boardVector.dy());

            for (int i = 1; i < absDy; i++) {
                path.add(current.move(i, -i));
            }
            return path;
        }
    };

    private final NumberState xState;
    private final NumberState yState;

    Quadrant(NumberState xState, NumberState yState) {
        this.xState = xState;
        this.yState = yState;
    }

    public static Quadrant findQuadrant(BoardVector boardVector) {
        NumberState xState = findNumberState(boardVector.dx());
        NumberState yState = findNumberState(boardVector.dy());
        return Arrays.stream(Quadrant.values())
                .filter(quadrant -> quadrant.xState == xState && quadrant.yState == yState)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 좌표 축을 찾지 못했습니다."));
    }


    abstract public List<BoardLocation> createAllPath(BoardLocation current, BoardVector boardVector);
}
