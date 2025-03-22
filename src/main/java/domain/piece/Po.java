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

public class Po implements Piece {

    private final Team team;

    public Po(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Node source, Node destination, Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Node sourceNode, Board board) {
        List<Node> candidates = new ArrayList<>();
        for (Direction direction : List.of(UP, RIGHT, DOWN, LEFT)) {
            findHurdle(sourceNode, direction, board, candidates);
        }
        return candidates;
    }

    private void findHurdle(Node currentNode,
                            final Direction direction, final Board board,
                            final List<Node> candidates) {
        if (!currentNode.hasEdgeByDirection(direction)) {
            return;
        }
        Node nextNode = currentNode.findNextNodeByDirection(direction);
        if (board.existsPo(nextNode)) {
            return;
        }
        if (board.existsPieceByNode(nextNode)) {
            findCandidates(nextNode, direction, board, candidates);
            return;
        }
        findHurdle(nextNode, direction, board, candidates);
    }

    private void findCandidates(Node currentNode,
                                final Direction direction, final Board board,
                                final List<Node> candidates) {
        if (!currentNode.hasEdgeByDirection(direction)) {
            return;
        }
        Node nextNode = currentNode.findNextNodeByDirection(direction);
        if (board.existsPo(nextNode)
                || (board.existsPieceByNode(nextNode) && board.hasPieceInTeam(nextNode, this.team))) {
            return;
        }
        if (board.existsPieceByNode(nextNode) && board.hasPieceInTeam(nextNode, this.team.inverse())) {
            candidates.add(nextNode);
            return;
        }
        candidates.add(nextNode);
        findCandidates(nextNode, direction, board, candidates);
    }

    @Override
    public PieceType type() {
        return PieceType.PO;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
