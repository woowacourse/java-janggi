package domain.piece;

import domain.Direction;
import domain.Palace;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private static final List<List<Direction>> PATHS = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN),
            List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position source) {
        Palace palace = Palace.getInstance();
        List<Position> routes = new ArrayList<>(movementStrategy.findRoute(PATHS, source));
        if (palace.isInPalace(source)) {
            routes.addAll(palace.findDiagonalRoutes(source));
        }
        return routes;
    }

    @Override
    public List<Position> findPathTo(Position source, Position target) {
        if (source.isDiagonalTo(target)) {
            return Palace.getInstance().findDiagonalPathTo(source, target);
        }
        return movementStrategy.findPathTo(PATHS, source, target);
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "车";
        }
        return "車";
    }

    @Override
    public double getScore() {
        return 13;
    }
}
