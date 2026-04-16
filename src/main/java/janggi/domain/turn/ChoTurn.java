package janggi.domain.turn;

import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;

public class ChoTurn extends Turn {
    @Override
    public Turn move(Position source, Position target, Board board) {
        Piece piece = board.getPiece(source);
        validateIsNull(piece);
        board.movePiece(source, target);

        if (board.isKingDead(Team.HAN)) {
            return new GameOverTurn(Team.CHO);
        }
        return new HanTurn();
    }

    @Override
    public Team getTeam() {
        return Team.CHO;
    }
}
