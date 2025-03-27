package fixture;

import domain.board.Board;
import domain.board.PathFinder;
import domain.board.PathFinderFactory;
import domain.piece.Piece;
import domain.point.Point;
import java.util.Map;

public class BoardFixture {

    private static final PathFinderFactory pathFinderFactory = new PathFinderFactory();

    public static PathFinder createDefaultPathFinder() {
        return pathFinderFactory.createDefaultPathFinder();
    }

    public static Board createTestBoard(Map<Point, Piece> pieceByPoint) {
        return new Board(pieceByPoint, createDefaultPathFinder());
    }
}
