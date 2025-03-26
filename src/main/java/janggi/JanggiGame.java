package janggi;

import janggi.Team.Team;
import janggi.Team.Turn;
import janggi.board.Board;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final Board board;
    private final Turn turn;

    public JanggiGame(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public void move(final List<Position> positions) {
        board.move(positions, turn.getCurrentTeam());
        turn.turnOver();
    }

    public boolean canContinueGame() {
        return board.hasEachKing();
    }

    public Team getWinningTeam() {
        return board.findWinningTeam();
    }

    public Team getCurrentTeam() {
        return turn.getCurrentTeam();
    }

    public Map<Position, Piece> getPieces() {
        return board.getPieces();
    }
}
