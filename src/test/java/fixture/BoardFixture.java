package fixture;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Node;
import domain.board.Point;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class BoardFixture {

    public static Board createEmptyBoard() {
        BoardGenerator boardGenerator = new BoardGenerator();
        Map<Point, Node> nodeByPoint = boardGenerator.initializeNodesAndEdges();
        return new Board(new HashMap<>(), nodeByPoint);
    }

    public static Board createTestBoard(Map<Point, Piece> pieceByPoint) {
        BoardGenerator boardGenerator = new BoardGenerator();
        Map<Point, Node> nodeByPoint = boardGenerator.initializeNodesAndEdges();
        return new Board(pieceByPoint, nodeByPoint);
    }
}
