package domain.piece.path;

import domain.piece.Piece;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class PalaceValidator implements PathValidator {
    @Override
    public void validatePath(TeamType teamType, Position to, List<Position> intermediatePositions,
                             Map<Position, Piece> alivePieces) {
        boolean destinationInPalace = isDestinationInPalace(teamType, to);
        boolean teamAtPosition = isTeamAtPosition(teamType, to, alivePieces);
        boolean hasBlockedPieces = hasBlockedPieces(intermediatePositions, alivePieces);
        if (!destinationInPalace || teamAtPosition || hasBlockedPieces) {
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
    }

    private boolean isDestinationInPalace(TeamType teamType, Position destination) {
        if (teamType == TeamType.CHO) {
            return destination.isInChoPalace();
        }
        return destination.isInHanPalace();
    }

    private boolean isTeamAtPosition(TeamType teamType, Position destination, Map<Position, Piece> alivePieces) {
        Piece destinationPiece = alivePieces.getOrDefault(destination, null);
        if (destinationPiece == null) {
            return false;
        }
        return destinationPiece.isSameTeam(teamType);
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
