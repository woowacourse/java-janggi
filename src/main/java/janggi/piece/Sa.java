package janggi.piece;

import janggi.Team.Team;
import java.util.List;
import java.util.Set;

public class Sa extends Piece {

    private static final List<Integer> FORWARD = List.of(-1, 0);
    private static final List<Integer> RIGHT = List.of(0, 1);
    private static final List<Integer> LEFT = List.of(0, -1);
    private static final List<Integer> BACKWARD = List.of(1, 0);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            FORWARD, RIGHT, LEFT, BACKWARD
    );

    public Sa(Team team) {
        super(PieceType.SA, team);
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 사는 한 방향으로 한 칸만 이동할 수 있습니다.");
        }
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !AVAILABLE_DIFFERENCE.contains(List.of(differenceForY, differenceForX));
    }
}
