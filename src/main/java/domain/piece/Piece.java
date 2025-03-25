package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public abstract class Piece {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public abstract List<Position> calculatePath(Position startPosition, Position targetPosition);

    public Team getTeam() {
        return team;
    }

    public boolean isTeam(Piece otherPiece) {
        return isTeam(otherPiece.team);
    }

    public boolean isTeam(Team team) {
        return this.team == team;
    }
}
