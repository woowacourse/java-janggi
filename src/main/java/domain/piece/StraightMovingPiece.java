package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Offset;

import java.util.ArrayList;
import java.util.List;

public abstract class StraightMovingPiece extends Piece{
    public StraightMovingPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    public List<Offset> getPathOffset(Offset offset) {
        validateMoveRule(offset);
        Direction mainDirection = offset.getMainDirection();

        int distance = offset.calculateStraightDistance();

        return generateRoute(mainDirection, distance);
    }

    private void validateMoveRule(Offset offset) {
        if (!offset.isStraightMoving()) {
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
}
