package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Edge;
import domain.board.Node;
import java.util.List;

public class Wang implements Piece {

    private final Team team;

    public Wang(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Node source, Node destination, Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Node currentNode, Board board) {
        return currentNode.edges().stream()
                .filter(edge -> !board.hasTeamPieceByNode(edge.nextNode(), this.team)
                        || !board.existsPieceByNode(edge.nextNode()))
                .map(Edge::nextNode)
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
