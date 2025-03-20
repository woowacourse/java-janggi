package domain.board;

import domain.Directions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private final Point point;
    private final List<Edge> edges;

    public Node(Point point) {
        this.point = point;
        this.edges = new ArrayList<>();
    }

    public boolean hasEdgeByDirection(Direction direction) {
        return edges.stream()
                .anyMatch(edge -> edge.direction() == direction);
    }

    private Edge findEdgeByDirection(Direction direction) {
        return edges.stream()
                .filter(edge -> edge.direction() == direction)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 방향의 엣지가 존재하지 않습니다."));
    }

    public Node findNextNodeByDirection(Direction direction) {
        Edge edge = findEdgeByDirection(direction);
        return edge.nextNode();
    }

    public boolean canMoveByPath(Directions directions) {
        Node currentNode = this;
        for (Direction direction : directions.directions()) {
            if (!currentNode.hasEdgeByDirection(direction)) {
                return false;
            }
            currentNode = currentNode.findNextNodeByDirection(direction);
        }
        return true;
    }

    public Node moveByPath(Directions directions) {
        Node currentNode = this;
        for (Direction direction : directions.directions()) {
            currentNode = currentNode.findNextNodeByDirection(direction);
        }
        return currentNode;
    }

    public void addAllEdges(List<Edge> edges) {
        this.edges.addAll(edges);
    }

    public List<Edge> edges() {
        return edges;
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
}
