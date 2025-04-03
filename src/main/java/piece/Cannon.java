package piece;

import direction.Movement;
import direction.Point;
import team.Team;

public class Cannon extends Piece {

    public Cannon(Team team, Point current) {
        super(PieceType.CANNON, team, current);
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
        int count = 0;
        for (int moveCount = 0; moveCount < distance.moveCount(distance); moveCount++) {
            tempCurrent = tempCurrent.move(direction);
            if (allPieces.isExistPieceInPoint(tempCurrent)) {
                Piece piece = allPieces.findByPoint(tempCurrent);
                validateIsNotCannon(piece);
                count++;
            }
        }

        validateOverOnePiece(count);

        current = new Point(destination.column(), destination.row());
    }

    private void validatePossibleDiagonalMovePoint() {
        if (!current.isPalaceCenter() && !current.isPalaceCorner()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    @Override
    public int score() {
        return 7;
    }

    private void validateOverOnePiece(final int count) {
        if (count != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 하나 넘어야 합니다.");
        }
    }

    private void validateIsNotCannon(final Piece piece) {
        if (isSameType(piece)) {
            throw new IllegalArgumentException("[ERROR] 포가 존재하여 움직일 수 없습니다.");
        }
    }

    private void validateInvalidDestination(final Point destination) {
        if ((!current.isPalace() || !destination.isPalace()) && current.isDifferentColumn(destination) && current.isDifferentRow(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }
}
