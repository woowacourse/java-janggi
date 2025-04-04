package janggi.domain.piece.direction;

import janggi.domain.value.JanggiPosition;
import java.util.ArrayList;
import java.util.List;

public enum GungDirection {
    // 1칸 직선 이동
    RIGHT(1, 0),
    LEFT(-1, 0),
    UP(0, 1),
    DOWN(0, -1),

    // 2칸 직선 이동 (경로 포함)
    RIGHT_RIGHT(2, 0, new DirectionVector(1, 0)),
    LEFT_LEFT(-2, 0, new DirectionVector(-1, 0)),
    UP_UP(0, 2, new DirectionVector(0, 1)),
    DOWN_DOWN(0, -2, new DirectionVector(0, -1)),

    // 1칸 대각선 이동
    UP_RIGHT(1, 1),
    UP_LEFT(-1, 1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1),

    // 2칸 대각선 이동 (경로 포함)
    UP_RIGHT_TWO(2, 2, new DirectionVector(1, 1)),
    UP_LEFT_TWO(-2, 2, new DirectionVector(-1, 1)),
    DOWN_RIGHT_TWO(2, -2, new DirectionVector(1, -1)),
    DOWN_LEFT_TWO(-2, -2, new DirectionVector(-1, -1));

    private final DirectionVector destination;
    private final List<DirectionVector> intermediates;

    GungDirection(final int dx, final int dy) {
        this.destination = new DirectionVector(dx, dy);
        this.intermediates = List.of();
    }

    GungDirection(final int dx, final int dy, final DirectionVector intermediate) {
        this.destination = new DirectionVector(dx, dy);
        this.intermediates = List.of(intermediate);
    }

    public static List<JanggiPosition> of(JanggiPosition start, JanggiPosition end) {
        int dx = end.x() - start.x();
        int dy = end.y() - start.y();
        DirectionVector difference = new DirectionVector(dx, dy);

        for (GungDirection gungDirection : values()) {
            if (gungDirection.destination.equals(difference)) {
                return buildPath(start, gungDirection);
            }
        }
        throw new IllegalArgumentException("[ERROR] 궁성 내에서 이동 가능한 경로가 없습니다.");
    }

    private static List<JanggiPosition> buildPath(JanggiPosition start, GungDirection dir) {
        List<JanggiPosition> path = new ArrayList<>();

        // 중간 위치 추가
        for (DirectionVector pos : dir.intermediates) {
            path.add(new JanggiPosition(start.x() + pos.x(), start.y() + pos.y()));
        }

        // 최종 위치 추가
        path.add(new JanggiPosition(start.x() + dir.destination.x(), start.y() + dir.destination.y()));

        return path;
    }
}