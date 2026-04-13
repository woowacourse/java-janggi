package domain.piece.rule;

import domain.move.PalaceDiagonalMovement;
import domain.move.StraightMovement;
import domain.piece.Cannon;
import domain.piece.Piece;
import domain.piece.error.PieceException;
import java.util.List;

public class CannonRule extends MovementPieceRule {

    public CannonRule() {
        super(List.of(
                new StraightMovement(),
                new PalaceDiagonalMovement()
        ));
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        validateExactlyOneBridge(piecesOnPath);
        validateBridgeIsNotCannon(piecesOnPath);
    }

    public void validateTarget(Piece piece) {
        if (piece instanceof Cannon) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private void validateBridgeIsNotCannon(List<Piece> pieces) {
        boolean hasCannon = pieces.stream()
                .anyMatch(piece -> piece instanceof Cannon);
        if (hasCannon) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private void validateExactlyOneBridge(List<Piece> pieces) {
        if (pieces.size() != 1) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }
}
