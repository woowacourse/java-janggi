package domain.piece;

import java.util.List;

public record Pieces(List<Piece> pieces) {

    public Piece findByPosition(final Position position) {
        return pieces.stream()
                .filter(element -> element.getPosition().equals(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
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

    public boolean existGeneral() {
        return pieces.stream()
                .anyMatch(piece -> piece.isEqualType(PieceType.GENERAL));
    }

    public boolean isCannonByPosition(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .anyMatch(piece -> piece.isEqualType(PieceType.CANNON));
    }
}
