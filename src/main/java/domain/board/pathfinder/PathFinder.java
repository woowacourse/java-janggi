package domain.board.pathfinder;

import domain.point.Direction;
import domain.point.Node;
import domain.point.Path;
import domain.point.Point;
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

    public boolean hasNextPoint(final Point point, final Direction direction) {
        Node node = getNodeByPoint(point);
        return node.hasNextNode(direction);
    }

    public Point getNextPoint(final Point point, final Direction direction) {
        Node node = getNodeByPoint(point);
        Node nextNode = node.getNextNodeByDirection(direction);
        return getPointByNode(nextNode);
    }

    public boolean canMoveByPath(final Point point, final Path path) {
        Node node = getNodeByPoint(point);
        return node.canMoveByPath(path);
    }

    public Point getPointMovedByPath(final Point point, final Path path) {
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
