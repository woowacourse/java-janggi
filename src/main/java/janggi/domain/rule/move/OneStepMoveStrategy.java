package janggi.domain.rule.move;

import janggi.domain.Position;
import janggi.domain.rule.Movement;

public class OneStepMoveStrategy implements MoveStrategy {
    @Override
    public void validateCorrectRule(final Position departure, final Position destination, final Movement movement) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);

        if (Math.abs(diffRow) + Math.abs(diffColumn) != 1) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }
}
