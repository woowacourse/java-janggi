package janggi.domain.Turn;

import janggi.domain.board.Board;
import janggi.domain.position.Position;

public class GameOver implements GameState {

    private final Board board;

    public GameOver(Board board) {
        this.board = board;
    }

    @Override
    public GameState move(Position from, Position to) {
        throw new IllegalArgumentException("게임이 종료되었습니다.");
    }
}
