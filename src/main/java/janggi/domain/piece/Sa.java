package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Set;

public class Sa extends GungSungPiece {

    private static final List<Integer> FORWARD = List.of(-1, 0);
    private static final List<Integer> RIGHT = List.of(0, 1);
    private static final List<Integer> LEFT = List.of(0, -1);
    private static final List<Integer> BACKWARD = List.of(1, 0);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            FORWARD, RIGHT, LEFT, BACKWARD
    );

    public Sa(TeamType teamType) {
        super(PieceType.SA, teamType);
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition) {
        validateOutOfGungSung(arrivalPosition);
        return makePathForGungSungPiece(currentPosition, arrivalPosition);
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 사는 이어진 선을 따라 한 칸만 이동할 수 있습니다.");
        }
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !AVAILABLE_DIFFERENCE.contains(List.of(differenceForY, differenceForX));
    }

    private void validateOutOfGungSung(Position arrivalPosition) {
        if (!Position.isInGungSung(arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 사는 궁성 밖을 벗어날 수 없습니다.");
        }
    }
}
