package domain.piece;

import domain.board.Palace;
import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.List;

public class Cannon extends Piece {

    private static final Palace PALACE = new Palace();

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        validateLocation(from, to);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

    private void validateLocation(Coordination from, Coordination to) {
        boolean movable = from.isSameRowDifferentColumn(to)
                || from.isSameColumnDifferentRow(to)
                || PALACE.hasDiagonalRoute(from, to);
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
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        if (PALACE.hasDiagonalRoute(from, to)) {
            return PALACE.diagonalPath(from, to);
        }
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
