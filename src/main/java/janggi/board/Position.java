package janggi.board;

public record Position(int x, int y) {

    public Position update(Position position) {
        return new Position(position.x(), position.y());
    }
}
