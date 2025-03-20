package fixture;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Node;
import domain.board.Point;
import java.util.HashMap;
import java.util.Map;

public class BoardFixture {

    public static Board createEmptyBoard() {
        BoardGenerator boardGenerator = new BoardGenerator();
        Map<Point, Node> nodeByPoint = boardGenerator.initializeNodesAndEdges();
        return new Board(new HashMap<>(), nodeByPoint);
    }
}
