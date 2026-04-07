package janggi.model.turn;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.absolute.Position;

public class GameOver implements Turn {

    private final Board board;

    public GameOver(Board board) {
        this.board = board;
    }

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
    public Team getWinner() {
        if (board.isWinnerDetermined()) {
            return board.winner();
        }

        int scoreOfCho = getTotalScoreOf(Team.CHO);
        int scoreOfHan = getTotalScoreOf(Team.HAN);

        if (scoreOfCho <= scoreOfHan) {
            return Team.HAN;
        }

        return Team.CHO;
    }

    private int getTotalScoreOf(Team team) {
        return board.getBoardInfo().values().stream()
                .filter(value -> value.isSameTeam(team))
                .mapToInt(value -> value.getPieceType().getScore())
                .sum();
    }
}
