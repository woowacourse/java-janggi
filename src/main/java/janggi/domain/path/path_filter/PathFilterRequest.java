package janggi.domain.path.path_filter;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public record PathFilterRequest(
        Piece piece,
        List<Piece> allyPieces,
        List<Piece> enemyPieces
) {
    public List<Piece> allPieces() {
        final List<Piece> pieces = new ArrayList<>();
        pieces.addAll(allyPieces);
        pieces.addAll(enemyPieces);
        return pieces;
    }

    public List<Position> allPositions() {
        final List<Position> positions = new ArrayList<>();
        positions.addAll(allyPieces.stream().map(Piece::getPosition).toList());
        positions.addAll(enemyPieces.stream().map(Piece::getPosition).toList());
        return positions;
    }

    public List<Position> sameTypePositions() {
        return allPieces().stream()
                .filter(other -> other.getPieceType() == piece.getPieceType())
                .map(Piece::getPosition)
                .toList();
    }
}
