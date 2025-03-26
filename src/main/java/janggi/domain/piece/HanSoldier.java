package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.List;
import java.util.Set;

public class HanSoldier extends Soldier {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.DOWN),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Point start, Point end) {
        return moveStrategy.isMovable(janggiBoard, start, end, PATHS);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
