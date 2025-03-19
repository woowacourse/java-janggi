package domain;

public interface Piece {
    boolean isMovable(final Distance distance);

    boolean isGreenTeam();
}
