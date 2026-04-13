package domain.strategy;

import domain.game.Position;
import domain.game.Side;
import domain.game.Palace;
import java.util.List;

public class PalaceSoldierDiagonalStrategy implements MovementStrategy {
    private final Side side;

    public PalaceSoldierDiagonalStrategy(Side side) {
        this.side = side;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        int forwardDy = side.soldierForward().getDy();

        return Palace.diagonals(current).stream()
                .filter(direction -> direction.getDy() == forwardDy)
                .filter(current::canMove)
                .map(current::move)
                .filter(Palace::isPalace)
                .map(next -> new Path(List.of(next)))
                .toList();
    }
}
