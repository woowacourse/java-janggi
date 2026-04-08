package domain.piece;

import domain.Direction;
import domain.Offset;
import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class StraightMovingPiece extends Piece {
    public StraightMovingPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    protected void validateMoveRule(Position from, Position to, Optional<Palace> palace) {
        Offset offset = Offset.of(from, to);
        if (!isValidMove(offset)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }

        if (offset.isDiagonalMoving()) {
            validateDiagonalMoveInPalace(from, to, palace);
        }
    }

    private void validateDiagonalMoveInPalace(Position from, Position to, Optional<Palace> palace) {
        if (palace.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        Palace currentPalace = palace.get();
        validateBothPositionInPalace(from, to, currentPalace);
        if (!isValidDiagonalPath(from, to, currentPalace)) {
            throw new IllegalStateException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    @Override
    protected List<Offset> generatePaths(Position from, Position to, Optional<Palace> palace) {
        Offset offset = Offset.of(from, to);
        Direction direction = Direction.of(offset);
        int distance = offset.calculateDistance();
        return generateRoute(direction, distance);
    }

    private List<Offset> generateRoute(Direction mainDirection, int distance) {
        Offset step = new Offset(0, 0);
        List<Offset> route = new ArrayList<>();

        for (int i = 0; i < distance - 1; i++) {
            step = step.add(mainDirection.unit());
            route.add(step);
        }
        return route;
    }

    private boolean isValidDiagonalPath(Position from, Position to, Palace palace) {
        return (palace.isCenter(from) && palace.isCorner(to))
                || (palace.isCorner(from) && palace.isCenter(to)
                || (palace.isCorner(from) && palace.isCorner(to))
        );
    }

    private void validateBothPositionInPalace(Position from, Position to, Palace palace) {
        if (!(palace.isInPalace(from) && palace.isInPalace(to))) {
            throw new IllegalStateException("출발지 또는 목적지가 궁성이 아닙니다.");
        }
    }

    @Override
    protected boolean isValidMove(Offset offset) {
        return (offset.isStraightMoving() || offset.isDiagonalMoving());
    }
}
