import java.util.List;

public class Board {

    private static final int WIDTH_SIZE = 9;
    private static final int HEIGHT_SIZE = 10;

    List<Piece> pieces;

    public Board(List<Piece> es) {
        this.pieces = es;
    }

    public boolean isInboard(Position position) {
        return position.x() < WIDTH_SIZE && position.x() >= 0
            && position.y() < HEIGHT_SIZE && position.y() >= 0;
    }

    public boolean hasPieceOn(Position nextPos) {
        return pieces.stream()
            .anyMatch(piece -> piece.onPosition(nextPos));
    }
}
