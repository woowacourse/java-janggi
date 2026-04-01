package model.piece;

import model.Team;
import model.coordinate.Direction;
import model.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

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
        int absRowDiff = Math.abs(rowDiff);
        int absColDiff = Math.abs(colDiff);
        return (absColDiff >= 1 && absRowDiff == 0) || (absColDiff == 0 && absRowDiff >= 1);
    }
}
