package domain.piece;

import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Pieces {
    Piece getPiece(Position position);

    Topology getTopology();

    default Map<Position, Piece> collectPieces(List<Path> paths) {
        Map<Position, Piece> result = new HashMap<>();
        for (Path path : paths) {
            for (Position position : path.getPositions()) {
                result.put(position, getPiece(position));
            }
        }
        return result;
    }
}
