package model.piece;

import java.util.ArrayList;
import java.util.List;
import model.Team;
import model.coordinate.Direction;
import model.coordinate.Position;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team, PieceType.CANNON);
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        Direction direction = Direction.from(current, next);
        List<Position> path = new ArrayList<>();
        Position step = current.move(direction);
        while (!step.equals(next)) {
            path.add(step);
            step = step.move(direction);
        }
        return path;
    }

    @Override
    protected boolean isReachable(int rowDiff, int colDiff) {
        return (colDiff >= 1 && rowDiff == 0) || (colDiff == 0 && rowDiff >= 1);
    }
}
