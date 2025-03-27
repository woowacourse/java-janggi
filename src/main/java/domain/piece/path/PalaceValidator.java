package domain.piece.path;

import domain.piece.Piece;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class PalaceValidator implements PathValidator{
    private static int CHO_PALACE_MIN_ROW = 0;
    private static int CHO_PALACE_MAX_ROW = 2;
    private static int HAN_PALACE_MIN_ROW = 7;
    private static int HAN_PALACE_MAX_ROW = 9;
    private static int PALACE_MIN_COLUMN = 3;
    private static int PALACE_MAX_COLUMN = 5;

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
            return destination.isInRange(CHO_PALACE_MIN_ROW, CHO_PALACE_MAX_ROW, PALACE_MIN_COLUMN, PALACE_MAX_COLUMN);
        }
        return destination.isInRange(HAN_PALACE_MIN_ROW, HAN_PALACE_MAX_ROW, PALACE_MIN_COLUMN, PALACE_MAX_COLUMN);
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
