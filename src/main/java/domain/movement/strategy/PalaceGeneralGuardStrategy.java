package domain.movement.strategy;

import domain.common.Direction;
import domain.common.Position;
import domain.common.Side;
import domain.board.Palace;
import domain.movement.Path;
import java.util.ArrayList;
import java.util.List;

public class PalaceGeneralGuardStrategy implements MovementStrategy {
    private final Side side;

    public PalaceGeneralGuardStrategy(Side side) {
        this.side = side;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        if (!Palace.isOwnPalace(side, current)) {
            return List.of();
        }

        List<Path> result = new ArrayList<>();
        addLinear(current, result);
        addDiagonal(current, result);
        return result;
    }

    private void addLinear(Position current, List<Path> result) {
        Direction.linear().stream()
                .filter(current::canMove)
                .map(current::move)
                .filter(next -> Palace.isOwnPalace(side, next))
                .map(next -> new Path(List.of(next)))
                .forEach(result::add);
    }

    private void addDiagonal(Position current, List<Path> result) {
        Palace.diagonals(current).stream()
                .filter(current::canMove)
                .map(current::move)
                .filter(next -> Palace.isOwnPalace(side, next))
                .map(next -> new Path(List.of(next)))
                .forEach(result::add);
    }
}
