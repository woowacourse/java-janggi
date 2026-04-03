package domain.board;

public record PositionPair(Position first, Position second) {
    public PositionPair {
        if (first.equals(second)) {
            throw new IllegalArgumentException("[ERROR] 같은 좌표로 경로를 만들 수 없습니다.");
        }

        if (putInAscendingOrder(first, second)) {
            Position temp = first;
            first = second;
            second = temp;
        }
    }

    private boolean putInAscendingOrder(Position first, Position second) {
        if (first.x() != second.x()) {
            return first.x() > second.x();
        }

        return first.y() > second.y();
    }
}
