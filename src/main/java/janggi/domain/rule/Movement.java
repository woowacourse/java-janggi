package janggi.domain.rule;

import java.util.List;

public enum Movement {
    STRAIGHT_DIAGONAL(List.of(1, 2)),
    STRAIGHT_DIAGONAL_DIAGONAL(List.of(2, 3)),
    ;

    private final List<Integer> distances;

    Movement(final List<Integer> distances) {
        validateSize(distances);
        this.distances = distances;
    }

    private void validateSize(final List<Integer> distances) {
        if (distances.size() != 2) {
            throw new IllegalArgumentException("Movement는 2개의 원소를 가진 리스트가 필요합니다.");
        }
    }

    public int getMinDistance() {
        return Math.min(distances.get(0), distances.get(1));
    }

    public int getMaxDistance() {
        return Math.max(distances.get(0), distances.get(1));
    }
}
