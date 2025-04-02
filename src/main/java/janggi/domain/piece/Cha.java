package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.List;

public class Cha extends GungSungPiece {

    public Cha(TeamType teamType) {
        super(PieceType.CHA, teamType);
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition) {
        return makePathForGungSungPiece(currentPosition, arrivalPosition);
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 차는 이어진 선을 따라서만 이동할 수 있습니다.");
        }
    }

    @Override
    boolean isDiagonalInGungSung(Position currentPosition, int differenceForY, int differenceForX) {
        return Position.isAbleToDiagonalMoveInGungSung(currentPosition) && AVAILABLE_DIFFERENCE_IN_GUNGSUNG.contains(
                List.of(calculateUnit(differenceForY), calculateUnit(differenceForX)));
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !((Math.abs(differenceForY) > 0 && Math.abs(differenceForX) == 0) ||
                (Math.abs(differenceForY) == 0 && Math.abs(differenceForX) > 0));
    }
}


