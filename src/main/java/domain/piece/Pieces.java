package domain.piece;

import domain.spatial.Position;
import java.util.List;

public record Pieces(
        List<Piece> pieces
) {

    public List<Position> getPiecePaths(final Position startPosition, final Position targetPosition) {
        Piece piece = findByPosition(startPosition);
        return piece.getPath(targetPosition);
    }

    public int countPiecesInPositions(final List<Position> positions) {
        return (int) positions.stream()
                .filter(position ->
                        pieces.stream().anyMatch(piece -> piece.isSamePosition(position)))
                .count();
    }

    public boolean existByPosition(final Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    public void validateMovePath(final Position startPosition, final int pathPieceCount) {
        Piece piece = findByPosition(startPosition);
        piece.validateMoveByPathPieceCount(pathPieceCount);
    }

    public boolean isCannonByPosition(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .anyMatch(Piece::isCannon);
    }

    public void updatePosition(final Position startPosition, final Position updatePosition) {
        Piece piece = findByPosition(startPosition);

        pieces.remove(piece);
        pieces.add(piece.updatePosition(updatePosition));
    }

    public void removePieceIfExists(final Position targetPosition) {
        if (existByPosition(targetPosition)) {
            deleteByPosition(targetPosition);
        }
    }

    public boolean existKing() {
        return pieces.stream()
                .anyMatch(Piece::isKing);
    }

    private void deleteByPosition(final Position position) {
        pieces.remove(findByPosition(position));
    }

    private Piece findByPosition(final Position position) {
        return pieces.stream()
                .filter(element -> element.getPosition().equals(position))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
