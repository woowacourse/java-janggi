package domain.piece;

import domain.move.MoveContext;
import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.List;

public class Guard extends Piece {
    public Guard(Team team) {
        super(team);
    }

    @Override
    public void validateRule(MoveContext moveContext) {
        if (!canMoveOneStepInPalace(moveContext)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GUARD;
    }

    @Override
    public List<Coordination> resolvePath(MoveContext moveContext) {
        return List.of();
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        if (!piecesOnPath.isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private boolean canMoveOneStepInPalace(MoveContext moveContext) {
        return moveContext.isOneStepMoveInSamePalace();
    }
}
