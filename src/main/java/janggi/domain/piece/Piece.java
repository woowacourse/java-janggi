package janggi.domain.piece;

import janggi.domain.board.BoardView;
import janggi.domain.mouveRule.MoveRule;
import janggi.domain.vo.position.Position;

public abstract class Piece {
    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(Team otherTeam) {
        return team == otherTeam;
    }

    public boolean isSameType(PieceType other) {return this.pieceType() == other;}

    public boolean isEmpty() {
        return false;
    }

    public Team getTeam() {
        return team;
    }

    public abstract PieceType pieceType();

    public boolean canMove(Position from, Position to, BoardView board) {
        return moveRule().canMove(from, to, board);
    }

    protected abstract MoveRule moveRule();

}
