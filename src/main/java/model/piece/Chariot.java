package model.piece;

import model.Team;
import model.coordinate.Direction;
import model.coordinate.PalacePositions;
import model.coordinate.Position;
import model.piece.strategy.reach.LinearReach;
import model.piece.strategy.reach.PalaceLinearReach;
import model.piece.strategy.reach.ReachStrategy;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private static final ReachStrategy DEFAULT = new LinearReach();
    private static final ReachStrategy PALACE = new PalaceLinearReach();

    public Chariot(Team team) {
        super(team, PieceType.CHARIOT);
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
    protected ReachStrategy determineReachStrategy(Position current, Position next) {
        if (PalacePositions.onPalaceDiagonal(current) && PalacePositions.onPalaceDiagonal(next)) {
            return PALACE;
        }
        return DEFAULT;
    }
}
