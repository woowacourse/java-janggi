package domain.board;

public record PositionPair(Position first, Position second) {

    public PositionPair(Position first, Position second) {
        validateDifferent(first, second);

        if (first.isAfter(second)) {
            this.first = second;
            this.second = first;
            return;
        }

        this.first = first;
        this.second = second;
    }

    private static void validateDifferent(Position first, Position second) {
        if (first.equals(second)) {
            throw new IllegalArgumentException("[ERROR] 같은 좌표로 경로를 만들 수 없습니다.");
        }
    }
}
