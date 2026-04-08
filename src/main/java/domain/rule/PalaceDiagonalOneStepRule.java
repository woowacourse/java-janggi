package domain.rule;

import domain.position.Palace;
import domain.position.Position;
import java.util.List;

public class PalaceDiagonalOneStepRule implements MoveRule {

    private final Palace palace;

    public PalaceDiagonalOneStepRule(Palace palace) {
        this.palace = palace;
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return palace.isDiagonalAdjacent(source, target);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        return List.of();
    }
}
