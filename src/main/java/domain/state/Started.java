package domain.state;

import domain.Board;
import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import java.util.Map;

public abstract class Started implements JanggiGame {
    protected final Board board;
    protected final Team turn;

    public Started(Board board, Team turn) {
        this.board = board;
        this.turn = turn;
    }

    @Override
    public double getScoreByTeam(Team team) {
        return board.getScoreByTeam(team);
    }

    @Override
    public Map<Position, Piece> getBoard() {
        return board.getPieces();
    }

    @Override
    public Team getTurn() {
        return turn;
    }
}
