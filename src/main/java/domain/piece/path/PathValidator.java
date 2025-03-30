package domain.piece.path;

import domain.piece.Piece;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public interface PathValidator {
    void validatePath(TeamType teamType, Position to, List<Position> intermediatePositions,
                      Map<Position, Piece> alivePieces);
}
