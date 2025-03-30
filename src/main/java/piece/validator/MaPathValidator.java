package piece.validator;

import static coordinate.CrossMoveVector.DOWN;
import static coordinate.CrossMoveVector.LEFT;
import static coordinate.CrossMoveVector.RIGHT;
import static coordinate.CrossMoveVector.UP;

import board.Board;
import coordinate.Coordinate;
import java.util.List;
import java.util.Set;

public class MaPathValidator implements PathValidator {

    @Override
    public boolean validate(Board board, Coordinate departure, Coordinate arrival) {
        return findPaths(departure, arrival)
                .stream()
                .noneMatch(board::hasPiece);
    }

    private Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int deltaX = arrival.getX() - departure.getX();
        int deltaY = arrival.getY() - departure.getY();

        if (Math.abs(deltaX) == 2 && deltaY != 0) {
            if (deltaX > 0) {
                return Set.of(departure.moveBy(List.of(RIGHT)));
            }
            return Set.of(departure.moveBy(List.of(LEFT)));
        }
        if (Math.abs(deltaY) == 2 && deltaX != 0) {
            if (deltaY > 0) {
                return Set.of(departure.moveBy(List.of(DOWN)));
            }
            return Set.of(departure.moveBy(List.of(UP)));
        }

        throw new IllegalStateException("유효하지 않은 좌표입니다.");
    }
}
