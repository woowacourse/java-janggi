package piece;

import game.Team;
import location.Position;

public abstract class Piece {
    private final int id;
    private final Team team;
    private boolean isCatch;

    protected Piece(int id, Team team) {
        this.id = id;
        this.team = team;
        this.isCatch = false;
    }

    public int getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public void move(Pieces pieces, Position destination) {
        validateDestination(destination);
        validatePaths(pieces, destination);
        updateCurrentPosition(destination);
    }

    public boolean isCatch() {
        return isCatch;
    }

    public void catchByOpponent() {
        isCatch = true;
    }

    public abstract Position getCurrentPosition();

    public abstract PieceType getPieceType();

    public abstract int getScore();

    public abstract boolean isPlacedAt(Position targetPosition);

    protected abstract void validateDestination(Position destination);

    protected abstract void validatePaths(Pieces pieces, Position destination);

    protected abstract void updateCurrentPosition(Position destination);
}
