package fixture;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Node;
import domain.board.Point;
import java.util.HashMap;
import java.util.Map;

public class BoardFixture {

    public static Board createEmptyBoard() {
        BoardInitializer boardInitializer = new BoardInitializer();
        Map<Point, Node> nodeByPoint = boardInitializer.initializeNodesAndEdges();
        return new Board(new HashMap<>(), nodeByPoint);
    }
}
