package janggi.domain.piece;

public record Position(int x, int y) {
    public Position add(Position firstRelativePosition) {
        return new Position(firstRelativePosition.x() + x, firstRelativePosition.y() + y);
    }
    //TODO: 검증 로
}
