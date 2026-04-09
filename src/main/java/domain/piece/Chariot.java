package domain.piece;

import domain.move.MoveContext;
import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public void validateRule(MoveContext moveContext) {
        validateLocation(moveContext);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CHARIOT;
    }

    private void validateLocation(MoveContext moveContext) {
        boolean movable = moveContext.isSameRowMove()
                || moveContext.isSameColumnMove()
                || moveContext.isPalaceDiagonalMove();
        if (!movable) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    @Override
    public List<Coordination> resolvePath(MoveContext moveContext) {
        if (moveContext.isPalaceDiagonalMove()) {
            return moveContext.palacePath();
        }
        Coordination from = moveContext.from();
        Coordination to = moveContext.to();
        if (from.isSameColumnDifferentRow(to)) {
            return from.betweenRowCoordination(to);
        }
        return from.betweenColumnCoordination(to);
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        if (!piecesOnPath.isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }
}
