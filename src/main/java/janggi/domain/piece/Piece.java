package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.mouveRule.MoveRule;
import janggi.domain.vo.Position;

public abstract class Piece {
    private final Team team;
//    private final MoveRule moveRule;

    public Piece(Team team) {
        this.team = team;

    }

    public boolean isSameTeam(Team otherTeam) {
        return team == otherTeam;
    }

    public boolean isEmpty() {
        return false;
    }

    public Team findTeam() {
        return team;
    }

    public abstract PieceType pieceType();

    public void move(Position from, Position to, Board board) {

        moveRule().move(from, to, board);
    }

    public abstract MoveRule moveRule();


}
