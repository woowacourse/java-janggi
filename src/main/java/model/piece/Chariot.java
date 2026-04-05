package model.piece;

import model.coordinate.Direction;
import model.coordinate.PalacePositions;
import model.coordinate.Position;
import model.game.Team;
import model.piece.strategy.LinearReach;
import model.piece.strategy.PalaceLinearReach;
import model.piece.strategy.ReachStrategy;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private static final ReachStrategy DEFAULT = new LinearReach();
    private static final ReachStrategy PALACE = new PalaceLinearReach();

    public Chariot(Team team) {
        super(team, PieceType.CHARIOT);
    }

    @Override
    public List<Position> extractPath(Position current, Position nextExcluded) {
        Direction direction = Direction.from(current, nextExcluded);
        List<Position> path = new ArrayList<>();
        Position step = current.move(direction);
        while (!step.equals(nextExcluded)) {
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
