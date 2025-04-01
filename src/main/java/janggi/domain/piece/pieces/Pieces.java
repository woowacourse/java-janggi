package janggi.domain.piece.pieces;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.PieceView;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Pieces implements PiecesView, Cloneable {

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

    public static PiecesView fromPieceViews(Collection<PieceView> pieces) {
        return from(pieces.stream().map(piece -> (Piece) piece).collect(Collectors.toList()));
    }

    public Pieces getMapWithoutPosition(Position position) {
        Pieces map = new Pieces(this);
        map.removeByPosition(position);
        return map;
    }

    public void put(Piece piece) {
        values.put(piece.getPosition(), piece);
    }

    public void removeByPosition(Position position) {
        values.remove(position);
    }

    public Piece findExistingByPosition(Position position) {
        if (!values.containsKey(position)) {
            throw new IllegalArgumentException("해당 위치엔 기물이 존재하지 않습니다.");
        }
        return values.get(position);
    }

    public Map<Position, Piece> getValues() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public boolean isEnemyOnDestination(Side side, Position destination) {
        return values.values().stream()
            .filter(onPathPiece -> onPathPiece.isSamePosition(destination))
            .anyMatch(piece -> piece.getSide() != side);
    }

    @Override
    public PiecesView getPiecesOnPath(Collection<Position> pathsToDestination) {
        return from(
            values.values().stream()
                .filter(piece -> pathsToDestination.contains(piece.getPosition()))
                .toList()
        );
    }

    @Override
    public boolean hasOnlyOnePiece() {
        return size() == 1;
    }

    @Override
    public Optional<PieceView> findByPosition(Position position) {
        return Optional.ofNullable(values.get(position));
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public int countByPieceType(PieceType pieceType) {
        return Math.toIntExact(
            values.values().stream()
                .filter(piece -> piece.getPieceType().equals(pieceType))
                .count()
        );
    }

    @Override
    public List<Side> findAllByPieceType(PieceType pieceType) {
        return values.values().stream()
            .filter(piece -> piece.getPieceType().equals(pieceType))
            .map(Piece::getSide)
            .toList();
    }

    @Override
    public boolean containsPieceType(PieceType pieceType) {
        return values.values().stream()
            .anyMatch(piece -> piece.getPieceType().equals(pieceType));
    }

    @Override
    public boolean isAllyOnDestination(Side side, Position destination) {
        return values.values().stream()
            .filter(onPathPiece -> onPathPiece.isSamePosition(destination))
            .anyMatch(piece -> piece.getSide() == side);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pieces pieces)) {
            return false;
        }
        return Objects.equals(values, pieces.values);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values);
    }

    @Override
    public Pieces clone() {
        try {
            return (Pieces) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("복제본을 생성할 수 없습니다.");
        }
    }
}
