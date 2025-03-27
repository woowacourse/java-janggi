package store;

import location.Position;
import java.util.List;
import java.util.Optional;
import piece.Piece;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public void add(Piece piece) {
        pieces.add(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Optional<Piece> findByPoint(Position targetPosition) {
        return pieces.stream()
                .filter(piece -> piece.isPlacedAt(targetPosition))
                .findAny();
    }

    public Piece getByPosition(Position targetPosition) {
        return pieces.stream()
                .filter(piece -> piece.isPlacedAt(targetPosition))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
    }

    public void delete(Piece piece) {
        pieces.remove(piece);
    }

    public boolean isContainedPieceAtPosition(Position targetPosition) {
        return pieces.stream()
                .anyMatch(piece -> piece.isPlacedAt(targetPosition));
    }

    public boolean isAlreadyPieceInPosition(Position targetPosition) {
        return pieces.stream()
                .anyMatch(piece -> piece.isPlacedAt(targetPosition));
    }

    public void checkNotExistedPieceInPosition(Position position) {
        if (isContainedPieceAtPosition(position)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
