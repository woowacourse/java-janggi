package fixture;

import domain.board.Board;
import domain.board.pathfinder.DefaultPathFinderFactory;
import domain.board.pathfinder.PathFinder;
import domain.piece.Piece;
import domain.point.Point;
import java.util.Map;

public class BoardFixture {

    private static final DefaultPathFinderFactory DEFAULT_PATH_FINDER_FACTORY = DefaultPathFinderFactory.getInstance();

    public static PathFinder createDefaultPathFinder() {
        return DEFAULT_PATH_FINDER_FACTORY.createPathFinder();
    }

    public static Board createTestBoard(Map<Point, Piece> pieceByPoint) {
        return new Board(pieceByPoint, createDefaultPathFinder());
    }
}
