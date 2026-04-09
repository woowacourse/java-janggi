package janggi.model.turn;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.Position;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GameOver implements Turn {
    private final Board board;
    private final Team winner;

    public GameOver(Board board, Team winner) {
        this.board = board;
        this.winner = winner;
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
    public void withBoard(BiConsumer<Board, Team> consumer) {
        throw new IllegalStateException("이미 게임이 종료됐습니다.");
    }

    @Override
    public void withScore(BiConsumer<Score, Score> consumer) {
        consumer.accept(board.calculateScore(Team.CHO), board.calculateScore(Team.HAN));
    }

    @Override
    public void withWinner(Consumer<Team> consumer) {
        consumer.accept(winner);
    }
}
