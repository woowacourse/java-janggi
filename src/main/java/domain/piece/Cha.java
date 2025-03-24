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

    public Cha(final Team team) {
        this.team = team;
    }

    @Override
    public List<Node> findMovableNodes(final Node sourceNode, final Board board) {
        List<Node> candidates = new ArrayList<>();
        CHA_MOVABLE_DIRECTIONS.stream()
                .filter(sourceNode::hasEdgeByDirection)
                .forEach(direction ->
                        findCandidates(sourceNode.findNextNodeByDirection(direction), board, direction, candidates));
        return candidates;
    }

    private void findCandidates(Node currentNode,
                                final Board board, final Direction direction,
                                final List<Node> candidates) {
        if (!board.hasPieceTeamByNode(currentNode, this.team)) {
            candidates.add(currentNode);
        }

        while (true) {
            if (!currentNode.hasEdgeByDirection(direction)) {
                break;
            }
            Node nextNode = currentNode.findNextNodeByDirection(direction);
            if (board.hasPieceTeamByNode(nextNode, this.team)) {
                break;
            }
            candidates.add(nextNode);
            currentNode = nextNode;
        }
    }

    @Override
    public PieceType type() {
        return PieceType.CHA;
    }

    @Override
    public boolean hasTeam(final Team team) {
        return this.team == team;
    }
}
