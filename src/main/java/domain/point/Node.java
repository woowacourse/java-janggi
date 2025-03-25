package domain.point;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private final List<Edge> edges;

    public Node() {
        this.edges = new ArrayList<>();
    }

    public boolean hasNextNode(Direction direction) {
        return edges.stream()
                .anyMatch(edge -> edge.direction() == direction);
    }

    private Edge getEdgeByDirection(Direction direction) {
        return edges.stream()
                .filter(edge -> edge.direction() == direction)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 방향의 엣지가 존재하지 않습니다."));
    }

    public Node getNextNodeByDirection(Direction direction) {
        Edge edge = getEdgeByDirection(direction);
        return edge.nextNode();
    }

    public boolean canMoveByPath(Path path) {
        Node currentNode = this;
        for (Direction direction : path.directions()) {
            if (!currentNode.hasNextNode(direction)) {
                return false;
            }
            currentNode = currentNode.getNextNodeByDirection(direction);
        }
        return true;
    }

    public Node getNodeMovedByPath(Path path) {
        Node currentNode = this;
        for (Direction direction : path.directions()) {
            currentNode = currentNode.getNextNodeByDirection(direction);
        }
        return currentNode;
    }

    public void addAllEdges(List<Edge> edges) {
        this.edges.addAll(edges);
    }
}
