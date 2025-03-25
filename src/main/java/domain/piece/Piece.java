package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    protected List<Position> convertToPath(List<Move> moves, Position startPosition) {
        List<Position> path = new ArrayList<>();
        Position positionInPath = startPosition;
        for (int i = 0; i < moves.size() - 1; i++) {
            positionInPath = positionInPath.movePosition(moves.get(i));
            path.add(positionInPath);
        }

        return path;
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
