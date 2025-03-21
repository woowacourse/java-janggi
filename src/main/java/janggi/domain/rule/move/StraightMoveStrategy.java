package janggi.domain.rule.move;

import janggi.domain.Position;
import janggi.domain.rule.Movement;

public class StraightMoveStrategy implements MoveStrategy {

    public void validateCorrectRule(Position departure, Position destination, Movement movement) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);

        if (Math.min(Math.abs(diffRow), Math.abs(diffColumn)) != movement.getMinDistance()) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }
}
