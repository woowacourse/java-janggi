package fixture;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Node;
import domain.board.Point;
import domain.piece.Piece;
import java.util.Map;

public class BoardFixture {

    private static final BoardGenerator BOARD_GENERATOR = new BoardGenerator();

    public static Map<Point, Node> createDefaultNodesByPoint() {
        return BOARD_GENERATOR.createDefaultNodesByPoint();
    }

    public static Board createTestBoard(Map<Point, Piece> pieceByPoint) {
        Map<Point, Node> nodeByPoint = BOARD_GENERATOR.createDefaultNodesByPoint();
        return new Board(pieceByPoint, nodeByPoint);
    }
}
