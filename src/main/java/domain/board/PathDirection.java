package domain.board;

import static util.NumberState.NEGATIVE;
import static util.NumberState.POSITIVE;
import static util.NumberState.ZERO;
import static util.NumberState.findNumberState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.NumberState;

public enum PathDirection {

    POSITIVE_X(POSITIVE, ZERO, (current, boardVector) -> {
        List<BoardLocation> path = new ArrayList<>();
        for (int i = 1; i < boardVector.getAbsDx(); i++) {
            path.add(current.moveHorizon(i));
        }
        return path;
    }),

    POSITIVE_Y(ZERO, POSITIVE, (current, boardVector) -> {
        List<BoardLocation> path = new ArrayList<>();
        for (int i = 1; i < boardVector.getAbsDy(); i++) {
            path.add(current.moveVertical(i));
        }
        return path;
    }),

    NEGATIVE_X(NEGATIVE, ZERO, (current, boardVector) -> {
        List<BoardLocation> path = new ArrayList<>();
        for (int i = 1; i < boardVector.getAbsDx(); i++) {
            path.add(current.moveHorizon(-i));
        }
        return path;
    }),


    NEGATIVE_Y(ZERO, NEGATIVE, (current, boardVector) -> {
        List<BoardLocation> path = new ArrayList<>();
        for (int i = 1; i < boardVector.getAbsDy(); i++) {
            path.add(current.moveVertical(-i));
        }
        return path;
    }),

    FIRST_QUADRANT(POSITIVE, POSITIVE, (current, boardVector) -> {
        List<BoardLocation> path = new ArrayList<>();
        for (int i = 1; i < boardVector.getAbsDy(); i++) {
            path.add(current.move(i, i));
        }
        return path;
    }),

    SECOND_QUADRANT(NEGATIVE, POSITIVE, (current, boardVector) -> {
        List<BoardLocation> path = new ArrayList<>();
        for (int i = 1; i < boardVector.getAbsDy(); i++) {
            path.add(current.move(-i, i));
        }
        return path;
    }),

    THIRD_QUADRANT(NEGATIVE, NEGATIVE, (current, boardVector) -> {
        List<BoardLocation> path = new ArrayList<>();
        for (int i = 1; i < boardVector.getAbsDy(); i++) {
            path.add(current.move(-i, -i));
        }
        return path;
    }),

    FOURTH_QUADRANT(POSITIVE, NEGATIVE, (current, boardVector) -> {
        List<BoardLocation> path = new ArrayList<>();
        for (int i = 1; i < boardVector.getAbsDy(); i++) {
            path.add(current.move(i, -i));
        }
        return path;
    });

    private final NumberState xState;
    private final NumberState yState;
    private final PathCreator pathCreator;

    PathDirection(NumberState xState, NumberState yState, PathCreator pathCreator) {
        this.xState = xState;
        this.yState = yState;
        this.pathCreator = pathCreator;
    }

    public List<BoardLocation> createPaths(BoardLocation current, BoardVector boardVector) {
        return pathCreator.create(current, boardVector);
    }

    public static PathDirection findPathDirection(BoardVector boardVector) {
        NumberState dxState = findNumberState(boardVector.dx());
        NumberState dyState = findNumberState(boardVector.dy());
        return Arrays.stream(values())
                .filter(path -> path.xState == dxState && path.yState == dyState)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동 경로의 방향을 찾지 못했습니다."));
    }
}
