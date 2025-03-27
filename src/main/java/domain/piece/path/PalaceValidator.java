package domain.piece.path;

import domain.piece.Piece;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class PalaceValidator implements PathValidator{
    @Override
    public void validatePath(Piece piece, Position to, List<Position> intermediatePositions,
                             Map<Position, Piece> alivePieces) {
        boolean destinationInPalace = isDestinationInPalace(piece, to);
        boolean teamAtPosition = isTeamAtPosition(piece, to, alivePieces);
        boolean hasBlockedPieces = hasBlockedPieces(intermediatePositions, alivePieces);
        if(!destinationInPalace || teamAtPosition || hasBlockedPieces){
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
    }

    private boolean isDestinationInPalace(Piece piece, Position destination) {
        if(piece.getTeamType()== TeamType.CHO){
            return destination.isInChoPalace();
        }
        return destination.isInHanPalace();
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
