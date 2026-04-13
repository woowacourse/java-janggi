package domain.piece;

import domain.Direction;
import domain.Palace;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position source) {
        List<List<Direction>> paths = buildPaths(source);
        return movementStrategy.findRoute(paths, source);
    }

    @Override
    public List<Position> findPathTo(Position source, Position target) {
        List<List<Direction>> paths = buildPaths(source);
        return movementStrategy.findPathTo(paths, source, target);
    }

    private List<List<Direction>> buildPaths(Position source) {
        List<List<Direction>> paths = new ArrayList<>();
        for (Direction direction : Palace.getInstance().getDirections(source)) {
            paths.add(List.of(direction));
        }
        return paths;
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "楚";
        }
        return "漢";
    }

    @Override
    public double getScore() {
        return 0;
    }
}
