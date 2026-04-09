package janggi.model.turn;

import janggi.model.ScorePolicy;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.absolute.Position;

public record GameOver(Board board) implements Turn {

    @Override
    public Turn play(Position from, Position to) {
        throw new IllegalStateException("게임 종료 후 턴을 수행할 수 없습니다.");
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    @Override
    public Board board() {
        throw new IllegalStateException("게임이 이미 종료됐습니다.");
    }

    @Override
    public boolean isChoTurn() {
        throw new IllegalStateException("게임이 이미 종료됐습니다.");
    }

    @Override
    public Team getWinner(ScorePolicy scorePolicy) {
        if (board.isWinnerDetermined()) {
            return board.winner();
        }

        float scoreOfCho = getTotalScoreOf(Team.CHO, scorePolicy);
        float scoreOfHan = getTotalScoreOf(Team.HAN, scorePolicy);

        if (scoreOfCho <= scoreOfHan) {
            return Team.HAN;
        }

        return Team.CHO;
    }

    private float getTotalScoreOf(Team team, ScorePolicy scorePolicy) {
        return scorePolicy.getScoreOf(board, team);
    }
}
