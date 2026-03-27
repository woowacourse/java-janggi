package domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = Map.copyOf(pieces);
    }

    public List<Piece> getBlockingPieces(Route route) {
        return route.intermeidateNodes().stream()
                .map(pieces::get)
                .filter(piece -> piece != null)
                .toList();
    }

    public Optional<Piece> getDestinationPiece(Route route) {
        return Optional.ofNullable(pieces.get(route.endPos()));
    }
}
