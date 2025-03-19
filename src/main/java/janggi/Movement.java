package janggi;

import java.util.List;

public class Movement {

    private final List<Integer> distances;

    public Movement(final List<Integer> distances) {
        validateSize(distances);
        this.distances = distances;
    }

    private void validateSize(final List<Integer> distances) {
        if (distances.size() != 2) {
            throw new IllegalArgumentException("Movement는 2개의 원소를 가진 리스트가 필요합니다.");
        }
    }

    public int getMinDistance() {
        return distances.stream()
                .mapToInt(x -> x)
                .min()
                .orElseThrow(IllegalStateException::new);
    }

    public int getMaxDistance() {
        return distances.stream()
                .mapToInt(x -> x)
                .max()
                .orElseThrow(IllegalStateException::new);
    }
}
