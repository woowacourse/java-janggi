package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Pieces {

    private final Map<Position, Piece> values;

    public Pieces(Map<Position, Piece> values) {
        this.values = values;
    }

    public Pieces(Pieces pieces) {
        this.values = new HashMap<>(pieces.values);
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

    public void put(Position position, Piece piece) {
        values.put(position, piece);
    }

    public boolean hasPieceOnPosition(Position position) {
        return values.containsKey(position);
    }

    public void removeByPosition(Position position) {
        values.remove(position);
    }

    public Piece findByPosition(Position position) {
        return values.get(position);
    }

    public int size() {
        return values.size();
    }

    public PieceType getPieceTypeOnPosition(Position position) {
        return values.get(position).getPieceType();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Map<Position, Piece> getValues() {
        return values;
    }

    public Piece findExistingByPosition(Position source) {
        if (!values.containsKey(source)) {
            throw new IllegalArgumentException("해당 위치엔 기물이 존재하지 않습니다.");
        }
        return values.get(source);
    }
}
