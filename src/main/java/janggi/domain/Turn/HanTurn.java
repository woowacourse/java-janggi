package janggi.domain.Turn;

import janggi.domain.board.Board;
import janggi.domain.position.Position;

public class HanTurn implements GameState {
    private final Board board;

    public HanTurn(Board board) {
        this.board = board;
    }

    @Override
    public GameState move(Position from, Position to) {
        board.move(from, to);
        // if 왕 먹혔으면 게임 종료 리턴
        if(board.isGameOver()) {
            return new GameOver(board);
        }

        return new ChoTurn(board);
    }
}
