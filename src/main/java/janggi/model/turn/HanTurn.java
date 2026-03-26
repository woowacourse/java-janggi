package janggi.model.turn;

import janggi.model.Board;
import janggi.model.Team;
import janggi.model.position.Position;
import java.util.function.Consumer;

public class HanTurn implements Turn {

    private final Board board;

    public HanTurn(Board board) {
        this.board = board;
    }

    @Override
    public Turn play(Position from, Position to) {
        Board movedBoard = board.move(
                Team.HAN,
                from,
                to
        );

        if (movedBoard.isGameOver()) {
            return new GameOver();
        }

        return new ChoTurn(movedBoard);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public void accept(Consumer<Board> consumer) {
        consumer.accept(board);
    }
}
