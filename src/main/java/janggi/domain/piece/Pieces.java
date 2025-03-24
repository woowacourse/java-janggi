package janggi.domain.piece;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Pieces {

    private final Map<Position, Piece> values;

    public Pieces(Map<Position, Piece> values) {
        this.values = values;
    }

    private Pieces(Pieces pieces) {
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

    public Pieces getMapWithoutPosition(int x, int y) {
        Pieces map = new Pieces(this);
        map.removeByPosition(x, y);
        return map;
    }

    public void put(Piece piece) {
        values.put(piece.getPosition(), piece);
    }

    public void removeByPosition(int x, int y) {
        values.remove(new Position(x, y));
    }

    public Optional<Piece> findByPosition(Position position) {
        return Optional.ofNullable(values.get(position));
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int countByPieceType(PieceType pieceType) {
        return Math.toIntExact(
            values.values().stream()
                .filter(piece -> piece.getPieceType().equals(pieceType))
                .count()
        );
    }

    public List<Side> findAllByPieceType(PieceType pieceType) {
        return values.values().stream()
            .filter(piece -> piece.getPieceType().equals(pieceType))
            .map(Piece::getSide)
            .toList();
    }

    public boolean containsPieceType(PieceType pieceType) {
        return values.values().stream()
            .anyMatch(piece -> piece.getPieceType().equals(pieceType));
    }

    public Piece findExistingByPosition(int x, int y) {
        Position position = new Position(x, y);
        if (!values.containsKey(position)) {
            throw new IllegalArgumentException("해당 위치엔 기물이 존재하지 않습니다.");
        }
        return values.get(position);
    }

    public Map<Position, Piece> getValues() {
        return values;
    }
}
