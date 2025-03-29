package piece;

import direction.Movement;
import direction.Point;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Movement> PATH = List.of(Movement.LEFT, Movement.RIGHT, Movement.UP, Movement.DOWN);

    public Chariot() {
    }

    public Chariot(final Point current) {
        super(current);
    }

    @Override
    public void move(final Pieces allPieces, final Point destination) {
        validateInvalidDestination(destination);
        Point distance = destination.minus(current);

        Movement direction = Movement.toDirection(distance);
        if (direction.isDiagonalMove()) {
            validatePossibleDiagonalMovePoint();
        }

        Point tempCurrent = new Point(current.column(), current.row());
        for (int moveCount = 1; moveCount < distance.moveCount(distance); moveCount++) {
            tempCurrent = tempCurrent.move(direction);
            validateIsExistPieceInPoint(allPieces, tempCurrent);
        }

        current = new Point(destination.column(), destination.row());
    }

    private void validatePossibleDiagonalMovePoint() {
        if ((!current.isPalaceCenter() && !current.isPalaceCorner())) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    @Override
    public int score() {
        return 13;
    }

    private void validateInvalidDestination(final Point destination) {
        if ((!current.isPalace() || !destination.isPalace()) && current.isDifferentColumn(destination) && current.isDifferentRow(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    private static void validateIsExistPieceInPoint(final Pieces pieces, final Point nextPoint) {
        if (pieces.isExistPieceIn(nextPoint)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
