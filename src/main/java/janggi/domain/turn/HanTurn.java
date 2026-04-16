package janggi.domain.turn;

import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;

public class HanTurn extends Turn {
    @Override
    public Turn move(Position source, Position target, Board board) {
        Piece piece = board.getPiece(source);
        validateIsNull(piece);
        board.movePiece(source, target);

        if (board.isKingDead(Team.CHO)) {
            return new GameOverTurn(Team.HAN);
        }
        return new ChoTurn();
    }

    @Override
    public Team getTeam() {
        return Team.HAN;
    }
}
