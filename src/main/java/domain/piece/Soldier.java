package domain.piece;

import domain.Direction;
import domain.Palace;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {

    private final List<List<Direction>> paths;

    public Soldier(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
        if (Side.CHO == side) {
            paths = List.of(
                    List.of(Direction.UP), List.of(Direction.RIGHT), List.of(Direction.LEFT)
            );
            return;
        }
        paths = List.of(
                List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
        );
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
        Palace palace = Palace.getInstance();
        if (!palace.isInPalace(source)) {
            return paths;
        }
        List<List<Direction>> paths = new ArrayList<>(this.paths);
        for (Direction direction : palace.getDirections(source)) {
            if (isForwardDiagonal(direction)) {
                paths.add(List.of(direction));
            }
        }
        return paths;
    }

    private boolean isForwardDiagonal(Direction direction) {
        if (isSameSide(Side.CHO)) {
            return direction == Direction.UP_LEFT || direction == Direction.UP_RIGHT;
        }
        return direction == Direction.DOWN_LEFT || direction == Direction.DOWN_RIGHT;
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "卒";
        }
        return "兵";
    }

    @Override
    public double getScore() {
        return 2;
    }
}
