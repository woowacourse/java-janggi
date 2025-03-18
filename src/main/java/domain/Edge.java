package domain;

public class Edge {

    private final Node nextNode;
    private final Direction direction;

    public Edge(Node nextNode, Direction direction) {
        this.nextNode = nextNode;
        this.direction = direction;
    }
}
