package domain.piece;

import domain.board.MoveContext;
import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public void validateRule(MoveContext moveContext) {
        validateLocation(moveContext);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
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
    public void validatePath(List<Piece> pieces) {
        validateExactlyOneBridge(pieces);
        validateBridgeIsNotCannon(pieces);
    }

    private void validateBridgeIsNotCannon(List<Piece> pieces) {
        boolean hasCannon = pieces.stream()
                .anyMatch(Piece -> Piece instanceof Cannon);
        if (hasCannon) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private static void validateExactlyOneBridge(List<Piece> pieces) {
        if (pieces.size() != 1) {
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
    public void validateNotSameTeam(Piece piece) {
        if (piece instanceof Cannon) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
        super.validateNotSameTeam(piece);
    }
}
