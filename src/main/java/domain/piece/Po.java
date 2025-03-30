package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Node;

import java.util.ArrayList;
import java.util.List;

public class Po extends Piece {

    public Po(final Team team) {
        super(team);
    }

    @Override
    public List<Node> findMovableNodes(final Node sourceNode, final Board board) {
        List<Node> candidates = new ArrayList<>();
        for (Direction direction : Direction.BASIC_DIRECTIONS) {
            findHurdle(sourceNode, direction, board, candidates);
        }
        return candidates;
    }

    private void findHurdle(Node currentNode,
                            final Direction direction, final Board board,
                            final List<Node> candidates) {
        while (currentNode.hasEdgeByDirection(direction)) {
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
        while (currentNode.hasEdgeByDirection(direction)) {
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
}
