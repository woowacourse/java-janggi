package janggi.piece;

import janggi.Team.Team;
import java.util.List;

public class Po extends Piece {

    public Po(Team team) {
        super(PieceType.PO, team);
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (isNotAbleToMoveDirection(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 포는 한 방향으로만 이동할 수 있습니다.");
        }
    }

    @Override
    public void validateExistPieceInPath(List<Piece> pieces, boolean hasPieceInArrivalPosition) {
        if (isConsecutiveWithOutLast(pieces, hasPieceInArrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }
        if (hasPo(pieces)) {
            throw new IllegalArgumentException("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
        }
    }

    private boolean isNotAbleToMoveDirection(int differenceForY, int differenceForX) {
        return !((Math.abs(differenceForY) > 0 && Math.abs(differenceForX) == 0) ||
                (Math.abs(differenceForY) == 0 && Math.abs(differenceForX) > 0));
    }

    private boolean hasPo(List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.matchPieceType(getPieceType()));
    }

    private boolean isConsecutiveWithOutLast(List<Piece> pieces, boolean hasPieceInArrivalPosition) {
        if (hasPieceInArrivalPosition) {
            return pieces.size() > 2;
        }
        return pieces.size() != 1;
    }
}
