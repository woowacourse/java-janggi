package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Node;
import java.util.Arrays;
import java.util.List;

public class Wang extends Piece {

    private static final List<Direction> WANG_MOVABLE_DIRECTIONS = Arrays.stream(Direction.values()).toList();

    public Wang(final Team team) {
        super(team);
    }

    @Override
    public List<Node> findMovableNodes(final Node currentNode, final Board board) {
        return WANG_MOVABLE_DIRECTIONS.stream()
                .filter(currentNode::hasEdgeByDirection)
                .map(currentNode::findNextNodeByDirection)
                .filter(nextNode -> !(board.existsPieceByNode(nextNode)
                        && board.hasPieceTeamByNode(nextNode, this.team))
                        && board.isPalaceArea(nextNode))
                .toList();
    }

    @Override
    public PieceType type() {
        return PieceType.WANG;
    }
}
