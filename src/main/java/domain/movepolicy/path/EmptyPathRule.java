package domain.movepolicy.path;

import domain.movepolicy.exception.InvalidPathRuleException;
import domain.movepolicy.exception.MovePolicyErrorMessage;
import domain.pieces.Piece;
import java.util.List;

public class EmptyPathRule implements PathRule {

    @Override
    public void validatePathPieces(List<Piece> pathPieces) {
        boolean hasBlockingPiece = pathPieces.stream()
                .anyMatch(piece -> !piece.isEmpty());
        if (hasBlockingPiece) {
            throw new InvalidPathRuleException(MovePolicyErrorMessage.PATH_MUST_BE_EMPTY);
        }
    }
}
