package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Node;

public class Sang implements Piece {

    private final Team team;

    public Sang(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Team team, Node source, Node destination, Board board) {
        return false;
    }

    @Override
    public PieceType type() {
        return PieceType.SANG;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
