package domain.board;

public class Edge {

    private final Node nextNode;
    private final Direction direction;

    public Edge(final Node nextNode, final Direction direction) {
        this.nextNode = nextNode;
        this.direction = direction;
    }

    public Node nextNode() {
        return nextNode;
    }

    public boolean isSameDirection(final Direction direction) {
        return this.direction == direction;
    }
}
