package vo;

import domain.board.Point;
import domain.pieces.Piece;
import java.util.List;
import java.util.Map;

public record BoardLocation(Point point, Piece piece) {

    public static List<BoardLocation> convertToLocations(Map<Point, Piece> board) {
        return board.entrySet().stream()
                .map(entry -> new BoardLocation(entry.getKey(), entry.getValue()))
                .toList();
    }

    public String getPiece() {
        return piece.getType().name();
    }

    public int getRow() {
        return point.row();
    }

    public int getColumn() {
        return point.column();
    }

    public int getPlayerId() {
        return piece.getPlayerId();
    }
}
