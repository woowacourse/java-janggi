package piece;

import direction.Point;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece findByPoint(Point point) {
        return pieces.stream()
                .filter(piece -> piece.isSamePoint(point))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다."));
    }

    public boolean isExistPieceIn(Point point) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePoint(point));
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
