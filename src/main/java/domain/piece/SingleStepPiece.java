package domain.piece;

import domain.Offset;
import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

import java.util.List;
import java.util.Optional;


public abstract class SingleStepPiece extends Piece {
    public SingleStepPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    protected List<Offset> generatePaths(Position from, Position to, Optional<Palace> palace) {
        return List.of();
    }

    @Override
    protected void validateMoveRule(Position from, Position to, Optional<Palace> palace) {
        Offset offset = Offset.of(from, to);
        if (!isValidMove(offset)) {
            throw new IllegalStateException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
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

    private boolean isValidDiagonalPath(Position from, Position to, Palace palace) {
        return (palace.isCenter(from) && palace.isCorner(to))
                || (palace.isCorner(from) && palace.isCenter(to));
    }

    private void validateBothPositionInPalace(Position from, Position to, Palace palace) {
        if (!(palace.isInPalace(from) && palace.isInPalace(to))) {
            throw new IllegalStateException("출발지 또는 목적지가 궁성이 아닙니다.");
        }
    }

    @Override
    protected boolean isValidMove(Offset offset) {
        return offset.equals(offset.normalize());
    }
}
