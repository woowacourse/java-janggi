package domain.piece.path;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CannonPathValidator implements PathValidator {
    @Override
    public void validatePath(TeamType teamType, Position to, List<Position> intermediatePositions,
                             Map<Position, Piece> alivePieces) {
        boolean teamOrCannonAtPosition = isTeamOrCannonAtPosition(teamType, to, alivePieces);
        boolean hasCannonPieceAtIntermediatePositions = hasCannonPieceAtIntermediatePositions(intermediatePositions,
                alivePieces);
        boolean hasOnlyOnePieceAtIntermediatePositions = hasOnlyOnePieceAtIntermediatePositions(intermediatePositions,
                alivePieces);
        if (teamOrCannonAtPosition || hasCannonPieceAtIntermediatePositions || hasOnlyOnePieceAtIntermediatePositions) {
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
    }

    private boolean isTeamOrCannonAtPosition(TeamType teamType, Position destination,
                                             Map<Position, Piece> alivePieces) {
        Piece destinationPiece = alivePieces.getOrDefault(destination, null);
        if (destinationPiece == null) {
            return false;
        }
        return destinationPiece.isSameTeam(teamType) || destinationPiece.isSameType(PieceType.CANNON);
    }

    private boolean hasCannonPieceAtIntermediatePositions(List<Position> intermediatePositions,
                                                          Map<Position, Piece> alivePieces) {
        Set<Position> positions = alivePieces.keySet();
        return intermediatePositions.stream()
                .filter(positions::contains)
                .anyMatch(position -> {
                    Piece piece = alivePieces.get(position);
                    return piece.isSameType(PieceType.CANNON);
                });
    }

    private boolean hasOnlyOnePieceAtIntermediatePositions(List<Position> intermediatePositions,
                                                           Map<Position, Piece> alivePieces) {
        return countBlockedPiece(intermediatePositions, alivePieces) != 1;
    }

    private int countBlockedPiece(List<Position> intermediatePositions, Map<Position, Piece> alivePieces) {
        Set<Position> positions = alivePieces.keySet();
        return (int) intermediatePositions.stream()
                .filter(positions::contains)
                .count();
    }

}
