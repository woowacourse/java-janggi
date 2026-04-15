package domain.rule;

import domain.position.Position;
import java.util.List;

public class ForwardAndSideRule implements MoveRule {

    private final int forwardDirection;

    public ForwardAndSideRule(int forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return isValidForwardMove(source, target) || isValidSideMove(source, target);
    }

    private boolean isValidForwardMove(Position source, Position target) {
        int rowDifference = target.rowDifference(source);
        int columnDifference = target.columnDifference(source);
        return rowDifference == forwardDirection && columnDifference == 0;
    }

    private boolean isValidSideMove(Position source, Position target) {
        int rowDifference = target.rowDifference(source);
        int columnDifference = target.columnDifference(source);
        return rowDifference == 0 && Math.abs(columnDifference) == 1;
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        return List.of();
    }
}
