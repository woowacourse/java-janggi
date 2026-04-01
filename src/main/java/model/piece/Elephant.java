package model.piece;

import model.Team;
import model.coordinate.Direction;
import model.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        List<Direction> directions = Direction.decomposePieceRoute(current, next);
        List<Position> path = new ArrayList<>();
        Position step = current;
        for (int i = 0; i < directions.size() - 1; i++) {
            step = step.move(directions.get(i));
            path.add(step);
        }
        return path;
    }

    @Override
    protected boolean isReachable(int rowDiff, int colDiff) {
        int absRowDiff = Math.abs(rowDiff);
        int absColDiff = Math.abs(colDiff);
        return (absColDiff == 3 && absRowDiff == 2) || (absColDiff == 2 && absRowDiff == 3);
    }
}
