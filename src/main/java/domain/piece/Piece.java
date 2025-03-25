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

    public boolean compareTeam(Piece otherPiece) {
        return compareTeam(otherPiece.team);
    }

    public boolean compareTeam(Team team) {
        return this.team == team;
    }
}
