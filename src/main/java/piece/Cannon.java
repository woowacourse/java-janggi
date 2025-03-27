package piece;

import direction.Movement;
import direction.Point;

public class Cannon extends Piece {

    public Cannon(String nickname, Point current) {
        super(nickname, current);
    }

    @Override
    public void move(Pieces pieces, Point destination) {
        validateInvalidDestination(destination);
        Point distance = destination.minus(current);

        Movement direction = Movement.toDirection(distance);
        Point tempCurrent = new Point(current.column(), current.row());
        int count = 0;
        for (int moveCount = 0; moveCount < distance.moveCount(distance); moveCount++) {
            tempCurrent = tempCurrent.move(direction);
            if (pieces.isExistPieceIn(tempCurrent)) {
                Piece piece = pieces.findByPoint(tempCurrent);
                validateIsNotCannon(piece);
                count++;
            }
        }

        validateOverOnePiece(count);

        current = new Point(destination.column(), destination.row());
    }

    private static void validateOverOnePiece(int count) {
        if (count != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 하나 넘어야 합니다.");
        }
    }

    private void validateIsNotCannon(Piece piece) {
        if (piece.equals(this)) {
            throw new IllegalArgumentException("[ERROR] 포가 존재하여 움직일 수 없습니다.");
        }
    }

    private void validateInvalidDestination(Point destination) {
        if (current.isDifferentColumn(destination) && current.isDifferentRow(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }
}
