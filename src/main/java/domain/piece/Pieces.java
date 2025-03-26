package domain.piece;

import domain.MoveInfos;
import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.List;

public record Pieces(
        List<Piece> pieces
) {

    public List<Position> getPiecePaths(final Position startPosition, final Position targetPosition) {
        Piece piece = findByPosition(startPosition);
        return piece.getPaths(targetPosition);
    }

    public PieceCategory getCategoryAtPosition(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .map(Piece::getCategory)
                .findFirst()
                .orElse(PieceCategory.NONE);
    }

    public void movePiece(final Position startPosition, final Position targetPosition, final MoveInfos moveInfos) {
        Piece pieceToMove = findByPosition(startPosition);
        Piece movedPiece = pieceToMove.move(targetPosition, moveInfos);

        pieces.remove(pieceToMove);
        pieces.add(movedPiece);
    }

    public boolean existKing() {
        return pieces.stream()
                .anyMatch(Piece::isKing);
    }

    public void removePieceIfExists(final Position targetPosition) {
        if (existByPosition(targetPosition)) {
            deleteByPosition(targetPosition);
        }
    }

    public boolean existByPosition(final Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    private void deleteByPosition(final Position position) {
        pieces.remove(findByPosition(position));
    }

    private Piece findByPosition(final Position position) {
        return pieces.stream()
                .filter(element -> element.getPosition().equals(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 좌표입니다."));
    }
}
