package janggi.domain.path.path_filter;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record PathFilterRequest(
        Piece piece,
        List<Piece> allyPieces,
        List<Piece> enemyPieces
) {
    public List<Piece> allPieces() {
        return Stream.of(allyPieces, enemyPieces)
                .flatMap(Collection::stream)
                .toList();
    }

    public List<Position> allPositions() {
        return allPieces().stream()
                .map(Piece::getPosition)
                .toList();
    }

    public List<Position> sameTypePositions() {
        return allPieces().stream()
                .filter(other -> other.getPieceType() == piece.getPieceType())
                .map(Piece::getPosition)
                .toList();
    }
}
