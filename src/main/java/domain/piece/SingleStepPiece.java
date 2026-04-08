package domain.piece;

import domain.Offset;
import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

import java.util.List;


public abstract class SingleStepPiece extends Piece {
    public SingleStepPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    protected List<Offset> generatePaths(Position from, Position to) {
        return List.of();
    }

    @Override
    protected void validateMoveRule(Position from, Position to) {
        Offset offset = Offset.of(from, to);
        if (Palace.isInAnyPalace(from) && isSingleDiagonalStep(offset)) {
            Palace.findPalace(from).validateDiagonalMoveRule(from, to);
            return;
        }
        if (!isSingleStep(offset)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    private boolean isSingleStep(Offset offset) {
        return (offset.absX() == 1 && offset.absY() == 0) || (offset.absX() == 0 && offset.absY() == 1);
    }

    private boolean isSingleDiagonalStep(Offset offset) {
        return (offset.absX() == 1 && offset.absY() == 1);
    }
}
