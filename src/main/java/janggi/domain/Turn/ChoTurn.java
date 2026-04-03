package janggi.domain.Turn;

import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import java.util.Map;

public class ChoTurn implements GameState {
    private final Board board;

    public ChoTurn(Board board) {
        this.board = board;
    }

    @Override
    public GameState move(Position from, Position to) {
        board.move(from, to);
        // if 왕 먹혔으면 게임 종료 리턴
        if(board.isGameOver()) {
            return new GameOver(board);
        }
        return new HanTurn(board);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Map<Position, Space> captureBoard() {
        return board.getPiecesInfo();
    }
}
