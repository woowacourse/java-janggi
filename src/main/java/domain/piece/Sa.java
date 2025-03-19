package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Node;

import java.util.ArrayList;
import java.util.List;

public class Sa implements Piece {

    private final Team team;

    public Sa(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Team team, Node source, Node destination, Board board) {
        return findMovableNodes(team, source, board).contains(destination);
    }

    private List<Node> findMovableNodes(Team team, Node currentNode, Board board) {
        List<Node> candidates = new ArrayList<>();
        currentNode.edges().stream()
                .filter(edge -> !board.existsPieceByTeam(edge.nextNode(), team) || !board.existsPiece(edge.nextNode()))
                .forEach(edge -> candidates.add(edge.nextNode()));

        return candidates;
    }

    @Override
    public PieceType type() {
        return PieceType.SA;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
