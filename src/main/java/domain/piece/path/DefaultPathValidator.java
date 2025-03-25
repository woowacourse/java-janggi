package domain.piece.path;

import domain.Position;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class DefaultPathValidator implements PathValidator {
    @Override
    public void validatePath(Piece piece, Position to, List<Position> intermediatePositions,
                             Map<Position, Piece> alivePieces) {
        if (hasBlockedPieces(intermediatePositions, alivePieces) || isTeamAtPosition(piece, to, alivePieces)) {
            throw new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다.");
        }
    }

    private boolean isTeamAtPosition(Piece piece, Position destination, Map<Position, Piece> alivePieces) {
        Piece destinationPiece = alivePieces.getOrDefault(destination, null);
        if (destinationPiece == null) {
            return false;
        }
        return destinationPiece.isSameTeam(piece);
    }

    private boolean hasBlockedPieces(List<Position> intermediatePositions, Map<Position, Piece> alivePieces) {
        return intermediatePositions.stream()
                .anyMatch(position -> hasPosition(position, alivePieces));
    }

    private boolean hasPosition(Position findPosition, Map<Position, Piece> alivePieces) {
        return alivePieces.keySet()
                .stream()
                .anyMatch(position -> position.equals(findPosition));
    }
}
