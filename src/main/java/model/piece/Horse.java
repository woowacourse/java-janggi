package model.piece;

import java.util.ArrayList;
import java.util.List;
import model.Team;
import model.coordinate.Direction;
import model.coordinate.Position;

public class Horse extends Piece {

    public Horse(Team team) {
        super(team, PieceType.HORSE);
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
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return (colDiff == 1 && rowDiff == 2) || (colDiff == 2 && rowDiff == 1);
    }
}
