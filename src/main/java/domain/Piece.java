package domain;

public abstract class Piece {
    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public void canMove(Position targetPosition) {
        // moveStrategy.movable();
    }
}
