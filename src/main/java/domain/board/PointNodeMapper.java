package domain.board;

import java.util.Map;
import java.util.stream.Collectors;

public class PointNodeMapper {

    private final Map<Point, Node> nodeByPoint;
    private final Map<Node, Point> pointByNode;

    public PointNodeMapper(Map<Point, Node> nodeByPoint) {
        this.nodeByPoint = nodeByPoint;
        this.pointByNode = nodeByPoint.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public boolean existsPoint(Point point) {
        return nodeByPoint.containsKey(point);
    }

    public boolean existsNode(Node node) {
        return pointByNode.containsKey(node);
    }

    public Node getNodeByPoint(Point point) {
        if (!existsPoint(point)) {
            throw new IllegalArgumentException("존재하지 않는 포인트입니다.");
        }
        return nodeByPoint.get(point);
    }

    public Point getPointByNode(Node node) {
        if (!existsNode(node)) {
            throw new IllegalArgumentException("존재하지 않는 노드입니다.");
        }
        return pointByNode.get(node);
    }
}
