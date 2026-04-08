package janggi.model.turn;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.Position;
import java.util.function.BiConsumer;

public class HanTurn implements Turn {

    private final Board board;

    public HanTurn(Board board) {
        this.board = board;
    }

    @Override
    public Turn play(Position from, Position to) {
        Board movedBoard = board.move(Team.HAN, from, to);
        if (movedBoard.isGameOver()) {
            return new GameOver(movedBoard);
        }
        return new ChoTurn(movedBoard);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public void withBoard(BiConsumer<Board, Team> consumer) {
        consumer.accept(board, Team.HAN);
    }

    @Override
    public void withScore(BiConsumer<Score, Score> consumer) {
        consumer.accept(board.calculateScore(Team.CHO), board.calculateScore(Team.HAN));
    }
}
