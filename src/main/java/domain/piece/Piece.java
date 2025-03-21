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

    public abstract boolean isCanon();

    public abstract boolean isKing();

    public Team getTeam() {
        return team;
    }

    public boolean compareTeam(Piece otherPiece) {
        return this.team == otherPiece.team;
    }
}
