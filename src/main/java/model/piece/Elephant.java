package model.piece;

import model.Team;
import model.coordinate.Direction;
import model.coordinate.Position;
import model.piece.strategy.AnimalReach;
import model.piece.strategy.ReachStrategy;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    private static final ReachStrategy DEFAULT = new AnimalReach(2);

    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    @Override
    protected ReachStrategy determineReachStrategy(Position current, Position next) {
        return DEFAULT;
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
}
