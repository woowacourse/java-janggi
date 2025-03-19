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
        for (int i = 0; i < moves.size() - 1; i++) {
            startPosition = startPosition.movePosition(moves.get(i));
            path.add(startPosition);
        }

        return path;
    }

    public abstract List<Position> calculatePath(Position startPosition, Position targetPosition);

    public abstract boolean isCanon();

    public Team getTeam() {
        return team;
    }

    public boolean compareTeam(Piece piece2) {
        return this.team == piece2.team;
    }
}
