package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Node;

public class Po implements Piece {

    private final Team team;

    public Po(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Node source, Node destination, Board board) {
        return false;
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
