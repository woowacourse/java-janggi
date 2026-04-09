package janggi.model.board;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.util.Map;

public record CompletedBoard(Team winner) implements Board {

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
    public int getMaterialScoreOf(Team team) {
        throw new IllegalArgumentException("이미 게임이 종료되었습니다.");
    }
}
