package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import janggi.team.TeamType;

public class Cha extends Piece {

    public Cha(TeamType teamType) {
        super(PieceType.CHA, teamType);
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition) {
        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        validateDistanceAndDirection(differenceForY, differenceForX);

        return new Path(calculateMovingPositions(currentPosition, arrivalPosition, differenceForY, differenceForX));
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 차는 한 방향으로만 이동할 수 있습니다.");
        }
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !((Math.abs(differenceForY) > 0 && Math.abs(differenceForX) == 0) ||
                (Math.abs(differenceForY) == 0 && Math.abs(differenceForX) > 0));
    }
}
