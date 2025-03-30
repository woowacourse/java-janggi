package domain.board;

import domain.Directions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private final Point point;
    private final List<Edge> edges;

    public Node(final Point point) {
        this.point = point;
        this.edges = new ArrayList<>();
    }

    public boolean isSameNode(final Node destination) {
        return this.equals(destination);
    }

    public boolean hasEdgeByDirection(final Direction direction) {
        return edges.stream()
                .anyMatch(edge -> edge.isSameDirection(direction));
    }

    public Node findNextNodeByDirection(final Direction direction) {
        Edge edge = findEdgeByDirection(direction);
        return edge.nextNode();
    }

    private Edge findEdgeByDirection(final Direction direction) {
        return edges.stream()
                .filter(edge -> edge.isSameDirection(direction))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 방향의 엣지가 존재하지 않습니다."));
    }

    public boolean canMoveByPath(final Directions directions) {
        Node currentNode = this;
        for (Direction direction : directions.directions()) {
            if (!currentNode.hasEdgeByDirection(direction)) {
                return false;
            }
            currentNode = currentNode.findNextNodeByDirection(direction);
        }
        return true;
    }

    public Node moveByPath(final Directions directions) {
        Node currentNode = this;
        for (Direction direction : directions.directions()) {
            currentNode = currentNode.findNextNodeByDirection(direction);
        }
        return currentNode;
    }

    public void addAllEdges(final List<Edge> edges) {
        this.edges.addAll(edges);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Node node)) {
            return false;
        }
        return Objects.equals(point, node.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }

    @Override
    public String toString() {
        return point.toString();
    }

    public Point point() {
        return point;
    }
}
