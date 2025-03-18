package janggi.domain;

public abstract class Piece {

    private Position position;
    private final Team team;

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public void move(final Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public boolean isSamePosition(final Position otherPosition) {
        return position.equals(otherPosition);
    }
}
