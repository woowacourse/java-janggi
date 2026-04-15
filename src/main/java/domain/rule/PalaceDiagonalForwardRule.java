package domain.rule;

import domain.position.Palace;
import domain.position.Position;
import java.util.List;

public class PalaceDiagonalForwardRule implements MoveRule {

    private final int forwardDirection;

    public PalaceDiagonalForwardRule(int forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDifference = target.rowDifference(source);
        return rowDifference == forwardDirection
                && Palace.all().stream()
                        .anyMatch(palace -> palace.isDiagonalAdjacent(source, target));
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        return List.of();
    }
}
