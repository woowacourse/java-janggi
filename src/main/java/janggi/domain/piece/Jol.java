package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Set;

public class Jol extends GungSungPiece {

    private static final List<Integer> FORWARD = List.of(-1, 0);
    private static final List<Integer> RIGHT = List.of(0, 1);
    private static final List<Integer> LEFT = List.of(0, -1);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            FORWARD, RIGHT, LEFT
    );

    public Jol() {
        super(PieceType.JOL, TeamType.CHO);
    }

    public Jol(TeamType teamType) {
        super(PieceType.JOL, TeamType.CHO);

        if (teamType != TeamType.CHO) {
            throw new IllegalArgumentException("[ERROR] 졸은 초나라의 기물입니다.");
        }
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition) {
        return makePathForGungSungPiece(currentPosition, arrivalPosition);
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 졸은 앞 방향으로 이어진 선을 따라 한 칸 씩만 이동할 수 있습니다.");
        }
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !AVAILABLE_DIFFERENCE.contains(List.of(differenceForY, differenceForX));
    }
}
