package domain.piece;

import domain.ErrorMessage;
import domain.Offset;

import java.util.List;

public abstract class SingleStepPiece extends Piece{
    public SingleStepPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    public List<Offset> getPathPositions(Offset offset) {

        int dx = offset.dx();
        int dy = offset.dy();

        if (!((Math.abs(dx) == 1 && Math.abs(dy) == 0) || (Math.abs(dx) == 0 && Math.abs(dy) == 1))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        return List.of();
    }

}
