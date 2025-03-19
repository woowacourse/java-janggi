package domain;

import java.util.Collections;
import java.util.List;

public class Node {

    private final Point point;
    private final List<Edge> edges;

    public Node(Point point, List<Edge> edges) {
        this.point = point;
        this.edges = Collections.unmodifiableList(edges);
    }
}
