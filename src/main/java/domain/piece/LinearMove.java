package domain.piece;

import domain.Direction;
import domain.JanggiBoard;
import domain.JanggiCoordinate;

public interface LinearMove {
    void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to);

    void validateReachAble(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to);

    default void validateRowCol(JanggiCoordinate from, JanggiCoordinate to) {
        if (!Direction.isSameRow(from, to) && !Direction.isSameCol(from, to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없습니다.");
        }
    }


}
