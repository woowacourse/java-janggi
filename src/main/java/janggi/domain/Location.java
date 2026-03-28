package janggi.domain;

import java.util.List;

public record Location(int x, int y) {

    private static final int COORDINATION_COUNT = 2;

    public static Location from(List<Integer> coordination) {
        validateCount(coordination);
        return new Location(coordination.get(0), coordination.get(1));
    }

    private static void validateCount(List<Integer> coordination) {
        if (coordination.size() != COORDINATION_COUNT) {
            throw new IllegalArgumentException("좌표의 개수는 " + COORDINATION_COUNT + "개 입니다.");
        }
    }

    public Location add(int dx, int dy) {
        return new Location(x + dx, y + dy);
    }

    public int calculateHorizontalDiff(Location to) {
        return to.y - this.y;
    }

    public int calculateVerticalDiff(Location to) {
        return to.x - this.x;
    }
}
