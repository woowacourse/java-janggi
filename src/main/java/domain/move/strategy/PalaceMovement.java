package domain.move.strategy;

import domain.board.Intersection;

public interface PalaceMovement {

    default Intersection toPalaceCenter(Intersection from) {
        if (!from.isPalaceCorner()) {
            throw new IllegalStateException("궁성의 코너에서만 중앙으로 이동할 수 있습니다.");
        }

        if (from.row() == 1 || from.row() == 3) {
            return new Intersection(2, 5);
        }

        return new Intersection(9, 5);
    }
}
