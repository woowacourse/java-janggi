package janggi.domain;

import java.util.List;

public record Location(int x, int y) {

    private static final int COORDINATE_COUNT = 2;

    public static Location from(List<Integer> position) {
        validateCount(position);
        return new Location(position.get(0), position.get(1));
    }

    private static void validateCount(List<Integer> position) {
        if (position.size() != COORDINATE_COUNT) {
            throw new IllegalArgumentException("좌표의 개수는 " + COORDINATE_COUNT + "개 입니다.");
        }
    }

    public Location add(int dx, int dy) {
        return new Location(x + dx, y + dy);
    }
}
