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

    public Po(final Team team) {
        this.team = team;
    }

    @Override
    public List<Node> findMovableNodes(final Node sourceNode, final Board board) {
        List<Node> candidates = new ArrayList<>();
        for (Direction direction : List.of(UP, RIGHT, DOWN, LEFT)) {
            findHurdle(sourceNode, direction, board, candidates);
        }
        return candidates;
    }

    private void findHurdle(Node currentNode,
                            final Direction direction, final Board board,
                            final List<Node> candidates) {
        while (true) {
            if (!currentNode.hasEdgeByDirection(direction)) {
                break;
            }
            Node nextNode = currentNode.findNextNodeByDirection(direction);
            if (board.hasPieceTypeByNode(nextNode, type())) {
                break;
            }
            if (board.existsPieceByNode(nextNode)) {
                findCandidates(nextNode, direction, board, candidates);
            }
            currentNode = nextNode;
        }
    }

    private void findCandidates(Node currentNode,
                                final Direction direction, final Board board,
                                final List<Node> candidates) {
        while (true) {
            if (!currentNode.hasEdgeByDirection(direction)) {
                break;
            }
            Node nextNode = currentNode.findNextNodeByDirection(direction);
            if (board.hasPieceTypeByNode(nextNode, type())
                    || (board.existsPieceByNode(nextNode) && board.hasPieceTeamByNode(nextNode, this.team))) {
                break;
            }
            if (board.existsPieceByNode(nextNode) && board.hasPieceTeamByNode(nextNode, this.team.inverse())) {
                candidates.add(nextNode);
                break;
            }
            candidates.add(currentNode);
            currentNode = nextNode;
        }
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
