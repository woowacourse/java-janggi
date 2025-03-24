package janggi.piece;

import janggi.Team.Team;
import java.util.List;
import java.util.Set;

public class Byeong extends Piece {

    private static final List<Integer> FORWARD = List.of(1, 0);
    private static final List<Integer> RIGHT = List.of(0, -1);
    private static final List<Integer> LEFT = List.of(0, 1);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            FORWARD, RIGHT, LEFT
    );

    public Byeong() {
        super(PieceType.BYEONG, Team.HAN);
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 병은 앞, 좌, 우로 한 칸 씩만 이동할 수 있습니다.");
        }
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !AVAILABLE_DIFFERENCE.contains(List.of(differenceForY, differenceForX));
    }
}
