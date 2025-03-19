package domain.board;

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
