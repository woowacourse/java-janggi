package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Offset;

import java.util.ArrayList;
import java.util.List;

public abstract class StraightMovingPiece extends Piece {
    public StraightMovingPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    public List<Offset> getPathOffset(Offset offset) {
        validateMoveRule(offset);
        Direction mainDirection = offset.getMainDirection();
        int distance = calculateStraightDistance(offset);

        return generateRoute(mainDirection, distance);
    }

    private void validateMoveRule(Offset offset) {
        if (!isStraightMoving(offset)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    private List<Offset> generateRoute(Direction mainDirection, int distance) {
        Offset step = new Offset(0, 0);
        List<Offset> route = new ArrayList<>();

        for (int i = 0; i < distance - 1; i++) {
            step = step.move(mainDirection);
            route.add(step);
        }
        return route;
    }

    private boolean isStraightMoving(Offset offset) {
        return (offset.absX() != 0 && offset.absY() == 0) || (offset.absX() == 0 && offset.absY() != 0);
    }

    private int calculateStraightDistance(Offset offset) {
        if (!isStraightMoving(offset)) {
            throw new IllegalStateException("직선 이동이 아닐 때는 직선 거리를 계산할 수 없습니다.");
        }
        return Math.max(offset.absX(), offset.absY());
    }
}
