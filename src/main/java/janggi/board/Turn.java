package janggi.board;

import janggi.piece.Piece;
import janggi.team.Team;

public class Turn {
    private final Team turn;

    public Turn(Team team) {
        this.turn = team;
    }

    public Turn() {
        this.turn = Team.CHO;
    }

    public Turn turnOver() {
        return new Turn(turn.turnOver());
    }

    public void checkTurn(Piece attackerPiece) {
        if (attackerPiece.isSameTeam(turn)) {
            return;
        }
        throw new IllegalArgumentException("순서를 확인하세요");
    }

    public Team getTurn() {
        return turn;
    }
}
