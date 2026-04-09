package domain.piece;

import domain.move.MoveContext;
import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.List;

public class General extends Piece {
    public General(Team team) {
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
        return PieceType.GENERAL;
    }

    @Override
    public boolean isAliveGeneral() {
        return true;
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
