package piece;

import direction.Point;
import java.util.List;
import java.util.Optional;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Optional<Piece> findByPoint(Point point) {
        return pieces.stream()
                .filter(piece -> piece.isEqualPositionWith(point))
                .findAny();
    }

    public Piece getByPoint(Point point) {
        return pieces.stream()
                .filter(piece -> piece.isEqualPositionWith(point))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
    }

    public boolean isContainPiece(Point point) {
        return pieces.stream()
                .anyMatch(piece -> piece.isEqualPositionWith(point));
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void validateNotContainPiece(Point point) {
        if (isContainPiece(point)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }

    public boolean isAlreadyPieceInPosition(Point point) {
        return pieces.stream()
                .anyMatch(piece -> piece.isEqualPositionWith(point));
    }
}
