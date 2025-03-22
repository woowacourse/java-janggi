package domain.piece;

import domain.spatial.Position;
import java.util.List;

public record Pieces(List<Piece> pieces) {

    public Piece findByPosition(final Position position) {
        return pieces.stream()
                .filter(element -> element.getPosition().equals(position))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int countPiecesInPositions(final List<Position> positions) {
        return (int) positions.stream()
                .filter(position ->
                        pieces.stream().anyMatch(piece -> piece.isSamePosition(position)))
                .count();
    }

    public void updatePosition(final Piece piece, final Position position) {
        pieces.remove(piece);
        pieces.add(piece.updatePosition(position));
    }

    public boolean existByPosition(final Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    public void deleteByPosition(final Position position) {
        pieces.remove(findByPosition(position));
    }

    public boolean existKing() {
        return pieces.stream()
                .anyMatch(Piece::isKing);
    }

    public boolean isCannonByPosition(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .anyMatch(Piece::isCannon);
    }
}
