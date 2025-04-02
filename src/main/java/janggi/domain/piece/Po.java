package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Set;

public class Po extends GungSungPiece {

    private static final List<Integer> FORWARD_RIGHT_DIAGONAL = List.of(1, 1);
    private static final List<Integer> FORWARD_LEFT_DIAGONAL = List.of(1, -1);
    private static final List<Integer> BACKWARD_RIGHT_DIAGONAL = List.of(-1, 1);
    private static final List<Integer> BACKWARD_LEFT_DIAGONAL = List.of(-1, -1);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE_IN_GUNGSUNG = Set.of(
            FORWARD_RIGHT_DIAGONAL, FORWARD_LEFT_DIAGONAL,
            BACKWARD_RIGHT_DIAGONAL, BACKWARD_LEFT_DIAGONAL
    );

    public Po(TeamType teamType) {
        super(PieceType.PO, teamType);
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition) {
        return makePathForGungSungPiece(currentPosition, arrivalPosition);
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (isNotAbleToMoveDirection(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 포는 이어진 선을 따라서만 이동할 수 있습니다.");
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

    @Override
    boolean isDiagonalInGungSung(Position currentPosition, int differenceForY, int differenceForX) {
        return Position.isAbleToDiagonalMoveInGungSung(currentPosition) && AVAILABLE_DIFFERENCE_IN_GUNGSUNG.contains(
                List.of(calculateUnit(differenceForY), calculateUnit(differenceForX)));
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
