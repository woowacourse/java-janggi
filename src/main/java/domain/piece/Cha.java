package domain.piece;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Node;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

    private static final List<Direction> CHA_MOVABLE_DIRECTIONS = List.of(UP, RIGHT, DOWN, LEFT);

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
        CHA_MOVABLE_DIRECTIONS.stream()
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
        if (board.hasTeamOfPiece(nextNode, this.team)) {
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
