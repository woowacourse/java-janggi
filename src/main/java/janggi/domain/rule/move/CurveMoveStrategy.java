package janggi.domain.rule.move;

import janggi.domain.Position;
import janggi.domain.rule.Movement;

public class CurveMoveStrategy implements MoveStrategy {

    @Override
    public void validateCorrectRule(final Position departure, final Position destination, Movement movement) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);

        int maxDiff = Math.max(Math.abs(diffRow), Math.abs(diffColumn));
        int minDiff = Math.min(Math.abs(diffRow), Math.abs(diffColumn));

        if (maxDiff != movement.getMaxDistance() || minDiff != movement.getMinDistance()) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }
}
