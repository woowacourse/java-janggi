package domain.piece;

import domain.ErrorMessage;
import domain.Offset;
import domain.board.Position;

import java.util.List;


public abstract class SingleStepPiece extends Piece {
    public SingleStepPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    protected List<Offset> generatePaths(Offset offset) {
        return List.of();
    }

    @Override
    protected void validateMoveRule(Position from, Position to) {
        Offset offset = Offset.of(from, to);
        if (!isSingleStep(offset)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    private boolean isSingleStep(Offset offset) {
        return (offset.absX() == 1 && offset.absY() == 0) || (offset.absX() == 0 && offset.absY() == 1);
    }
}
