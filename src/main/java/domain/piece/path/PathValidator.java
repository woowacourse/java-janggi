package domain.piece.path;

import domain.Position;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public interface PathValidator {
    void validatePath(Piece piece, Position to, List<Position> intermediatePositions, Map<Position, Piece> alivePieces);
}
