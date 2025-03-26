package domain.point;

import java.util.Map;
import java.util.stream.Collectors;

public class PathFinder {

    private final Map<Point, Node> nodeByPoint;
    private final Map<Node, Point> pointByNode;

    public PathFinder(final Map<Point, Node> nodeByPoint) {
        this.nodeByPoint = nodeByPoint;
        this.pointByNode = nodeByPoint.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public boolean existsPoint(final Point point) {
        return nodeByPoint.containsKey(point);
    }

    private boolean existsNode(final Node node) {
        return pointByNode.containsKey(node);
    }

    public boolean hasNextPoint(Point point, Direction direction) {
        Node node = getNodeByPoint(point);
        return node.hasNextNode(direction);
    }

    public Point getNextPoint(Point point, Direction direction) {
        Node node = getNodeByPoint(point);
        Node nextNode = node.getNextNodeByDirection(direction);
        return getPointByNode(nextNode);
    }

    public boolean canMoveByPath(Point point, Path path) {
        Node node = getNodeByPoint(point);
        return node.canMoveByPath(path);
    }

    public Point getPointMovedByPath(Point point, Path path) {
        Node node = getNodeByPoint(point);
        return getPointByNode(node.getNodeMovedByPath(path));
    }

    private Node getNodeByPoint(final Point point) {
        if (!existsPoint(point)) {
            throw new IllegalArgumentException("존재하지 않는 포인트입니다.");
        }
        return nodeByPoint.get(point);
    }

    private Point getPointByNode(final Node node) {
        if (!existsNode(node)) {
            throw new IllegalArgumentException("존재하지 않는 노드입니다.");
        }
        return pointByNode.get(node);
    }
}
