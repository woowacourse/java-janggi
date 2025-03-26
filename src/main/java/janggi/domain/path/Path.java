package janggi.domain.path;

import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public record Path(
        List<Position> pathPositions
) {
    public static Path start(final Position startPosition) {
        final List<Position> paths = new ArrayList<>();
        paths.add(startPosition);
        return new Path(paths);
    }

    public Optional<Path> nextPath(Movement movement) {
        final List<Position> result = movement.getPositionsWith(finalPosition());
        if (result.isEmpty()) return Optional.empty();
        return Optional.of(new Path(new ArrayList<>(result)));
    }

    public Path nextPath(Position position) {
        final List<Position> positions = new ArrayList<>();
        positions.addAll(pathPositions);
        positions.addAll(finalPosition().createPositionsUntil(position));
        positions.addLast(position);
        return new Path(new ArrayList<>(positions));
    }

    public List<Piece> getBlockedPiece(final List<Piece> pieces) {
        if (pieces.isEmpty()) return List.of();

        return pieces.stream()
                .filter(piece -> pathPositions.subList(0, pathPositions.size() - 1).contains(piece.getPosition()))
                .toList();
    }

    public int getMoveCount() {
        return pathPositions.size() - 1;
    }

    public boolean isBlockedWith(final List<Position> blockedPositions) {
        if (blockedPositions.isEmpty()) return false;

        return pathPositions.subList(0, pathPositions.size() - 1).stream()
                .anyMatch(blockedPositions::contains);
    }

    public boolean isStartWith(final Position position) {
        return pathPositions.getFirst().equals(position);
    }

    public boolean isEndWith(final List<Position> positions) {
        if (positions.isEmpty()) return false;
        return positions.contains(finalPosition());
    }

    public boolean isSuperPathOf(final Path path) {
        return new HashSet<>(this.pathPositions).containsAll(path.pathPositions);
    }

    public Position finalPosition() {
        return pathPositions.getLast();
    }

    public List<Path> subPaths() {
        List<Path> subPaths = new ArrayList<>();
        int size = pathPositions.size();

        for (int start = 0; start < size - 1; start++) {
            for (int end = start + 1; end < size; end++) {
                subPaths.add(new Path(pathPositions.subList(start, end + 1)));
            }
        }
        return subPaths;
    }
}
