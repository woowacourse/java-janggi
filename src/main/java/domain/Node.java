package domain;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private final Point point;
    private final List<Edge> edges;

    public Node(Point point, List<Edge> edges) {
        this.point = point;
        this.edges = new ArrayList<>(edges);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
}
