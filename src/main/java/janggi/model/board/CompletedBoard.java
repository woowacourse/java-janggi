package janggi.model.board;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.util.Map;

public class CompletedBoard implements Board {

    private final Team winner;

    public CompletedBoard(Team winner) {
        this.winner = winner;
    }

    @Override
    public Board move(Team team, Position from, Position to) {
        throw new IllegalArgumentException("이미 게임이 종료되었습니다.");
    }

    @Override
    public boolean isWinnerDetermined() {
        return true;
    }

    @Override
    public Map<Position, Piece> getBoardInfo() {
        throw new IllegalArgumentException("이미 게임이 종료되었습니다.");
    }

    @Override
    public Team winner() {
        return this.winner;
    }

    @Override
    public int getTotalScoreOf(Team team) {
        throw new IllegalArgumentException("이미 게임이 종료되었습니다.");
    }
}
