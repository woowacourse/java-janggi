package domain.piece;

import domain.board.Board;
import domain.board.Node;

import java.util.List;

public interface Piece {

    default boolean canMove(final Node source, final Node destination, final Board board) {
        return isDifferentNode(source, destination) && containsInCandidates(source, destination, board);
    }

    private boolean isDifferentNode(final Node source, final Node destination) {
        return !destination.isSameNode(source);
    }

    private boolean containsInCandidates(final Node source, final Node destination, final Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    List<Node> findMovableNodes(final Node source, final Board board);

    PieceType type();

    boolean hasTeam(final Team team);
}
