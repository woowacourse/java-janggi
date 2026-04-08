package domain.piece;

import domain.Direction;
import domain.board.Palace;
import exception.ErrorMessage;
import domain.Offset;
import domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class StraightMovingPiece extends Piece {
    public StraightMovingPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    protected void validateMoveRule(Position from, Position to) {
        Offset offset = Offset.of(from, to);
        if (Palace.isInAnyPalace(from) && offset.isDiagonalMoving()) {
            Palace.findPalace(from).validateDiagonalMoveRule(from, to);
            return;
        }
        if (!offset.isStraightMoving()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    @Override
    protected List<Offset> generatePaths(Position from, Position to) {
        Offset offset = Offset.of(from, to);
        if (offset.isDiagonalMoving()) {
            return Palace.findPalace(from).generatePaths(from, to);
        }
        Direction mainDirection = offset.getMainDirection();
        int distance = offset.calculateStraightDistance();
        return generateRoute(mainDirection, distance);
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
