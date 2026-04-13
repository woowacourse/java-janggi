package domain.movepolicy.path;

import domain.movepolicy.exception.InvalidPathRuleException;
import domain.movepolicy.exception.MovePolicyErrorMessage;
import domain.pieces.PieceType;
import java.util.List;
import domain.pieces.Piece;

public class PoPathRule implements PathRule {

    private static final int PATH_PIECES_SIZE_THRESHOLD = 1;

    @Override
    public void validatePathPieces(List<Piece> pathPieces) {
        List<Piece> pathFullPieces = pathPieces.stream()
                .filter(piece -> !piece.isEmpty())
                .toList();
        validateFullPieces(pathFullPieces);
    }

    private void validateFullPieces(List<Piece> pathFullPieces) {
        if (pathFullPieces.isEmpty()) {
            throw new InvalidPathRuleException(MovePolicyErrorMessage.PATH_MUST_CONTAIN_PIECE);
        }
        if (pathFullPieces.size() != PATH_PIECES_SIZE_THRESHOLD) {
            throw new InvalidPathRuleException(MovePolicyErrorMessage.PATH_MUST_CONTAIN_ONE_PIECE);
        }
        if (containsPoInPath(pathFullPieces)) {
            throw new InvalidPathRuleException(MovePolicyErrorMessage.PO_CANNOT_JUMP_OVER_PO);
        }
    }

    private static boolean containsPoInPath(List<Piece> pathPieces) {
        return isPoType(pathPieces.getFirst());
    }

    private static boolean isPoType(Piece piece) {
        return piece.getType() == PieceType.PO;
    }
}
