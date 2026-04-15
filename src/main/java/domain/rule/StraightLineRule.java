package domain.rule;

import domain.position.Position;
import java.util.List;

public class StraightLineRule implements MoveRule {

    @Override
    public boolean canMove(Position source, Position target) {
        if (source.equals(target)) {
            return false;
        }
        return source.isSameColumn(target) || source.isSameRow(target);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        if (source.isSameColumn(target)) {
            return source.makeRowStraightRoute(target);
        }
        return source.makeColumnStraightRoute(target);
    }
}
