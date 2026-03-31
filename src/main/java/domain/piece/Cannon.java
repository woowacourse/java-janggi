package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;
import java.util.Map;

import static util.ErrorMessage.IMPOSSIBLE_MOVE;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        validateLocation(from, to);
        validateOnlyOnePieceNotCannon(from, to, board);
        validateCanon(to, board);
        validateNotSameTeam(from, to, board);
    }

    private void validateCanon(Coordination to, Map<Coordination, Piece> board) {
        if (board.get(to).isCannon()) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    private void validateLocation(Coordination from, Coordination to) {
        boolean movable = from.isSameRowDifferentColumn(to) || from.isSameColumnDifferentRow(to);
        if (!movable) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    private void validateOnlyOnePieceNotCannon(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        List<Piece> piecesOnPath = getPieces(from, to, board);
        validateJumpRule(piecesOnPath);
    }

    private List<Piece> getPieces(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        return resolvePath(from, to).stream()
                .map(board::get)
                .filter(piece -> !piece.isEmpty())
                .toList();
    }

    private void validateJumpRule(List<Piece> pieces) {
        validateExactlyOneBridge(pieces);
        validateBridgeIsNotCannon(pieces);
    }

    private void validateBridgeIsNotCannon(List<Piece> pieces) {
        boolean hasCannon = pieces.stream()
                .anyMatch(Piece::isCannon);
        if (hasCannon) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    private static void validateExactlyOneBridge(List<Piece> pieces) {
        if (pieces.size() != 1) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    private List<Coordination> resolvePath(Coordination from, Coordination to) {
        if (from.isSameColumnDifferentRow(to)) {
            return from.betweenRowCoordination(to);
        }
        return from.betweenColumnCoordination(to);
    }
}
