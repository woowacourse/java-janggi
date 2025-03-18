import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Piece getPieceBy(Position position) {
        Piece piece = board.get(position);
        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다");
        }
        return piece;
    }
}
