package domain.piece;

import domain.Position;
import domain.Team;

public abstract class Piece {
    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public boolean canMove(Position currentPosition, Position targetPosition, PieceProvider pieceProvider) {
        return false;
    }

    public boolean isBridge() {
        return true;
    }

    public boolean isCatchByCannon() {
        return true;
    }

    public boolean isBlank() {
        return false;
    }
}
