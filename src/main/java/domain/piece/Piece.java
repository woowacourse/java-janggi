package domain.piece;

import domain.board.Board;
import domain.board.Node;

import java.util.List;

public interface Piece {

    default boolean canMove(Node source, Node destination, Board board) {
        return isDifferentNode(source, destination) && containsInCandidates(source, destination, board);
    }

    private boolean isDifferentNode(Node source, Node destination) {
        return !destination.isSameNode(source);
    }

    private boolean containsInCandidates(Node source, Node destination, Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    List<Node> findMovableNodes(Node source, Board board);

    PieceType type();

    boolean hasTeam(Team team);
}
