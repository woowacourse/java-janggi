package janggi.model.turn;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.Position;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ChoTurn implements Turn {

    private final Board board;

    public ChoTurn(Board board) {
        this.board = board;
    }

    @Override
    public Turn play(Position from, Position to) {
        Board movedBoard = board.move(Team.CHO, from, to);
        if (movedBoard.isGameOver()) {
            return new GameOver(movedBoard, Team.CHO);
        }
        return new HanTurn(movedBoard);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public void withBoard(BiConsumer<Board, Team> consumer) {
        consumer.accept(board, Team.CHO);
    }

    @Override
    public void withScore(BiConsumer<Score, Score> consumer) {
        consumer.accept(board.calculateScore(Team.CHO), board.calculateScore(Team.HAN));
    }

    @Override
    public void withWinner(Consumer<Team> consumer) {
        throw new IllegalStateException("아직 게임이 종료되지 않았습니다.");
    }
}
