package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Node;
import java.util.Arrays;
import java.util.List;

public class Wang implements Piece {

    private static final List<Direction> WANG_MOVABLE_DIRECTIONS = Arrays.stream(Direction.values()).toList();

    private final Team team;

    public Wang(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Node source, Node destination, Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Node currentNode, Board board) {
        return WANG_MOVABLE_DIRECTIONS.stream()
                .filter(currentNode::hasEdgeByDirection)
                .map(currentNode::findNextNodeByDirection)
                .filter(nextNode -> !(board.existsPieceByNode(nextNode)
                        && board.hasPieceInTeam(nextNode, this.team)))
                .toList();
    }

    @Override
    public PieceType type() {
        return PieceType.WANG;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
