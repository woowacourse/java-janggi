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
        if (mustStayInPalace()) {
            requireInPalace(from, to, palace);
        }
    }

    protected boolean mustStayInPalace() {
        return false;
    }

    private void validateDiagonalMoveInPalace(Position from, Position to, Optional<Palace> palace) {
        if (palace.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        Palace currentPalace = palace.get();
        currentPalace.requireBothInPalace(from, to);
        if (!currentPalace.isValidDiagonalPath(from, to)) {
            throw new IllegalStateException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    private void requireInPalace(Position from, Position to, Optional<Palace> palace) {
        if (palace.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        palace.get().requireBothInPalace(from, to);
    }

    @Override
    protected boolean isValidMove(Offset offset) {
        return offset.equals(offset.normalize());
    }
}
