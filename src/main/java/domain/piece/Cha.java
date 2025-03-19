package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Direction;
import domain.board.Node;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

    private final Team team;

    public Cha(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Node source, Node destination, Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Node sourceNode, Board board) {
        List<Node> candidates = new ArrayList<>(List.of(sourceNode));

        List<Direction> directions = List.of(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT);
        directions.stream()
                .filter(sourceNode::hasEdgeByDirection)
                .forEach(direction ->
                        findCandidates(sourceNode.findNextNodeByDirection(direction), board, direction, candidates));
        
        return candidates;
    }

    private void findCandidates(Node currentNode,
                                final Board board, final Direction direction,
                                final List<Node> candidates) {
        candidates.add(currentNode);
        if (!currentNode.hasEdgeByDirection(direction)) {
            return;
        }

        Node nextNode = currentNode.findNextNodeByDirection(direction);
        if (board.existsPieceByTeam(nextNode, this.team)) {
            return;
        }
        findCandidates(nextNode, board, direction, candidates);
    }

    @Override
    public PieceType type() {
        return PieceType.CHA;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
