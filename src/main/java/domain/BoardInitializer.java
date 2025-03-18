package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardInitializer {

    // 노드와 엣지 정보 초기화
    public void initializeNodesAndEdges() {
        Map<Point, Node> nodes = new HashMap<>();

        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {
            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                Point point = new Point(row, column);
                Node node = new Node(point, List.of());
                nodes.put(point, node);
            }
        }

        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {
            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                Point point = new Point(row, column);
                Node currentNode = nodes.get(point);

                for (Direction direction : List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)) {
                    int nextRow = row + direction.deltaRow();
                    int nextColumn = column + direction.deltaColumn();
                    if (!Board.isInRange(nextRow, nextColumn)) {
                        continue;
                    }
                    Node nextNode = nodes.get(new Point(nextRow, nextColumn));
                    Edge edge = new Edge(nextNode, direction);
                    currentNode.addEdge(edge);
                }
            }
        }
    }

    // 상마 순서에 따라 기물 배치
}

