package domain.piece;

import java.util.Collections;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece findByPosition(final Position position) {
        return pieces.stream()
                .filter(element -> element.getPosition().equals(position))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
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

    public boolean isCannonByPosition(Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .anyMatch(Piece::isCannon);
    }
}
