package domain.pathgenerator;

import common.exception.JanggiException;
import domain.direction.Direction;

import java.util.List;

public final class DirectionPath {

    private final List<Direction> directions;

    private DirectionPath(List<Direction> directions) {
        this.directions = List.copyOf(directions);
    }

    public static DirectionPath of(Direction... directions) {
        if (directions == null || directions.length == 0) {
            throw new JanggiException("이동 경로는 최소 1개 이상의 방향이 필요합니다.");
        }
        return new DirectionPath(List.of(directions));
    }

    public List<Direction> directions() {
        return directions;
    }
}

