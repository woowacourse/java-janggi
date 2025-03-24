package domain.board;

import static domain.board.Point.MAX_COLUMN_INDEX;
import static domain.board.Point.MAX_ROW_INDEX;
import static domain.board.Point.MIN_COLUMN_INDEX;
import static domain.board.Point.MIN_ROW_INDEX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointNodeMapperFactory {

    public PointNodeMapper createDefaultPointNodeMapper() {
        Map<Point, Node> nodeByPoint = new HashMap<>();

        createAllNodes(nodeByPoint);
        createAllEdges(nodeByPoint);

        return new PointNodeMapper(nodeByPoint);
    }

    private void createAllNodes(Map<Point, Node> nodeByPoint) {
        for (int row = MIN_ROW_INDEX; row <= MAX_ROW_INDEX; row++) {
            for (int column = MIN_COLUMN_INDEX; column <= MAX_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = new Node();
                nodeByPoint.put(point, currentNode);
            }
        }
    }

    private void createAllEdges(Map<Point, Node> nodeByPoint) {
        for (int row = MIN_ROW_INDEX; row <= MAX_ROW_INDEX; row++) {
            for (int column = MIN_COLUMN_INDEX; column <= MAX_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                Node currentNode = nodeByPoint.get(point);
                currentNode.addAllEdges(createEdgesByPoint(row, column, nodeByPoint));
            }
        }
    }

    private List<Edge> createEdgesByPoint(final int row, final int column,
                                          final Map<Point, Node> nodeByPoint) {
        List<Edge> edges = new ArrayList<>();

        for (Direction direction : Direction.VERTICALS) {
            int nextRow = row + direction.deltaRow();
            int nextColumn = column + direction.deltaColumn();
            if (!isInRange(nextRow, nextColumn)) {
                continue;
            }
            Node nextNode = nodeByPoint.get(Point.of(nextRow, nextColumn));
            edges.add(new Edge(nextNode, direction));
        }
        return edges;
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
