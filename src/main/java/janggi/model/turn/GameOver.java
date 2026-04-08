package janggi.model.turn;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.Position;
import java.util.function.BiConsumer;

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
    public void withBoard(BiConsumer<Board, Team> consumer) {
        throw new IllegalStateException("이미 게임이 종료됐습니다.");
    }

    @Override
    public void withScore(BiConsumer<Score, Score> consumer) {
        consumer.accept(board.calculateScore(Team.CHO), board.calculateScore(Team.HAN));
    }
}
