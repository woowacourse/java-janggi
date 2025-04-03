package domain.board.pathfinder;

import static domain.point.Point.MAX_COLUMN_INDEX;
import static domain.point.Point.MAX_ROW_INDEX;
import static domain.point.Point.MIN_COLUMN_INDEX;
import static domain.point.Point.MIN_ROW_INDEX;

import domain.point.Direction;
import domain.point.Edge;
import domain.point.Node;
import domain.point.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultPathFinderFactory implements PathFinderFactory {

    private static DefaultPathFinderFactory INSTANCE;

    private static Map<Point, Node> nodeByPoint;

    private DefaultPathFinderFactory() {
    }

    public static DefaultPathFinderFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DefaultPathFinderFactory();
            return INSTANCE;
        }
        return INSTANCE;
    }

    @Override
    public PathFinder createPathFinder() {
        if (nodeByPoint != null) {
            return new PathFinder(nodeByPoint);
        }

        nodeByPoint = new HashMap<>();
        createAllNodes(nodeByPoint);
        createAllEdges(nodeByPoint);
        return new PathFinder(nodeByPoint);
    }

    private void createAllNodes(final Map<Point, Node> nodeByPoint) {
        for (int row = MIN_ROW_INDEX; row <= MAX_ROW_INDEX; row++) {
            for (int column = MIN_COLUMN_INDEX; column <= MAX_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = new Node();
                nodeByPoint.put(point, currentNode);
            }
        }
    }

    private void createAllEdges(final Map<Point, Node> nodeByPoint) {
        final Point HAN_WANG_POINT = Point.of(2, 5);
        final Point CHO_WANG_POINT = Point.of(9, 5);

        createDiagonalEdges(HAN_WANG_POINT, nodeByPoint);
        createDiagonalEdges(CHO_WANG_POINT, nodeByPoint);

        for (int row = MIN_ROW_INDEX; row <= MAX_ROW_INDEX; row++) {
            for (int column = MIN_COLUMN_INDEX; column <= MAX_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = nodeByPoint.get(point);
                currentNode.addAllEdges(createVerticalEdges(point, nodeByPoint));
            }
        }
    }

    private List<Edge> createVerticalEdges(final Point point,
                                           final Map<Point, Node> nodeByPoint) {

        List<Edge> edges = new ArrayList<>();
        for (Direction direction : Direction.VERTICALS) {
            int nextRow = point.row() + direction.deltaRow();
            int nextColumn = point.column() + direction.deltaColumn();
            if (!isInRange(nextRow, nextColumn)) {
                continue;
            }
            Node nextNode = nodeByPoint.get(Point.of(nextRow, nextColumn));
            edges.add(new Edge(nextNode, direction));
        }
        return edges;
    }

    private void createDiagonalEdges(final Point point, final Map<Point, Node> nodeByPoint) {
        Node node = nodeByPoint.get(point);
        for (Direction direction : Direction.DIAGONALS) {
            int nextRow = point.row() + direction.deltaRow();
            int nextColumn = point.column() + direction.deltaColumn();
            if (!isInRange(nextRow, nextColumn)) {
                continue;
            }
            Node nextNode = nodeByPoint.get(Point.of(nextRow, nextColumn));
            node.addAllEdges(List.of(new Edge(nextNode, direction)));
            nextNode.addAllEdges(List.of(new Edge(node, direction.inverse())));
        }
    }

    private boolean isInRange(final int row, final int column) {
        try {
            Point.of(row, column);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
