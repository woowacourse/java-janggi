package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public class Cannon extends Piece{

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(Position startPosition, Position targetPosition) {
        return List.of();
    }

    @Override
    public boolean isCanon() {
        return true;
    }

}
