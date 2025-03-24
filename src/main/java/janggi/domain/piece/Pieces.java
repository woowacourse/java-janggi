package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Pieces {

    private final Map<Position, Piece> values;

    public Pieces(Map<Position, Piece> values) {
        this.values = values;
    }

    public static Pieces from(Collection<Piece> pieces) {
        return new Pieces(pieces.stream().collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    public boolean isEnemyOnDestination(Side side, Position destination) {
        return values.values().stream()
                .filter(onPathPiece -> onPathPiece.isSamePosition(destination))
                .anyMatch(piece -> piece.getSide() != side);
    }

    public Pieces getPiecesOnPath(Collection<Position> pathsToDestination) {
        return from(
                values.values().stream()
                        .filter(piece -> pathsToDestination.contains(piece.getPosition()))
                        .toList()
        );
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Map<Position, Piece> getValues() {
        return values;
    }
}
