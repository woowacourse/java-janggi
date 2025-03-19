package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public class Guard extends Piece{

    public Guard(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(Position startPosition, Position targetPosition) {
        return List.of();
    }

    @Override
    public boolean isCanon() {
        return false;
    }
}
