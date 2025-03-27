package piece;

import direction.Movement;
import direction.Point;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Movement> PATH = List.of(Movement.LEFT, Movement.RIGHT, Movement.UP, Movement.DOWN);

    public Chariot(String nickname, Point current) {
        super(nickname, current);
    }

    @Override
    public void move(Pieces pieces, Point destination) {
        validateInvalidDestination(destination);
        Point distance = destination.minus(current);

        Movement direction = Movement.toDirection(distance);
        Point tempCurrent = new Point(current.column(), current.row());
        for (int moveCount = 0; moveCount < distance.moveCount(distance); moveCount++) {
            tempCurrent = tempCurrent.move(direction);
            validateIsExistPieceInPoint(pieces, tempCurrent);
        }

        current = new Point(destination.column(), destination.row());
    }

    private void validateInvalidDestination(Point distance) {
        if (current.isDifferentColumn(distance) && current.isDifferentRow(distance)) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    private static void validateIsExistPieceInPoint(Pieces pieces, Point nextPoint) {
        if (pieces.isExistPieceIn(nextPoint)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
