package janggi.domain.piece;

public record Position(int x, int y) {
    public Position plus(Position other) {
        return new Position(other.x() + x, other.y() + y);
    }
    //TODO: 검증 로
}
