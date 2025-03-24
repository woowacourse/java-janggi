package janggi.piece;

import janggi.Team.Team;

public class Cha extends Piece {

    public Cha(Team team) {
        super(PieceType.CHA, team);
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
