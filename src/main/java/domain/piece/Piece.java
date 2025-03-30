package domain.piece;

import domain.board.Board;
import domain.board.Node;

import java.util.List;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public void validateMove(final Node source, final Node destination, final Board board) {
        if (!canMove(source, destination, board)) {
            throw new IllegalArgumentException(source + " -> " + destination + " [ERROR] 이동할 수 없는 경로입니다.");
        }
    }

    private boolean canMove(final Node source, final Node destination, final Board board) {
        return isDifferentNode(source, destination) && containsInCandidates(source, destination, board);
    }

    private boolean isDifferentNode(final Node source, final Node destination) {
        return !destination.isSameNode(source);
    }

    private boolean containsInCandidates(final Node source, final Node destination, final Board board) {
        return findMovableNodes(source, board).contains(destination);
    }

    public abstract List<Node> findMovableNodes(final Node source, final Board board);

    public boolean hasTeam(final Team team) {
        return this.team == team;
    }

    public abstract PieceType type();

    public Team team() {
        return this.team;
    }
}
