package location;

public record Position(int x, int y) {

    public Position apply(Direction direction) {
        return new Position(x + direction.getX(), y + direction.getY());
    }

    public void validateNotSame(Position position) {
        if (equals(position)) {
            throw new IllegalArgumentException("[ERROR] 출발지와 목적지가 똑같습니다.");
        }
    }
}
