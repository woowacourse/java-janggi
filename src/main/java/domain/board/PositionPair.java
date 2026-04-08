package domain.board;

import java.util.Objects;

public final class PositionPair {
    private final Position first;
    private final Position second;

    private PositionPair(Position first, Position second) {
        if (first.equals(second)) {
            throw new IllegalArgumentException("[ERROR] 같은 좌표로 경로를 만들 수 없습니다.");
        }

        this.first = first;
        this.second = second;
    }

    public static PositionPair ordered(Position first, Position second) {
        if (shouldSwap(first, second)) {
            return new PositionPair(second, first);
        }

        return new PositionPair(first, second);
    }

    private static boolean shouldSwap(Position first, Position second) {
        if (first.x() != second.x()) {
            return first.x() > second.x();
        }

        return first.y() > second.y();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PositionPair positionPair)) {
            return false;
        }

        return Objects.equals(first, positionPair.first)
                && Objects.equals(second, positionPair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
