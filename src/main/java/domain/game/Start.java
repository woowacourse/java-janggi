package domain.game;

import domain.JanggiBoard;
import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.HashMap;
import java.util.Map;

public class Start implements GameState {
    //    JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());
    JanggiBoard janggiBoard;

    @Override
    public GameState start(Map<JanggiPosition, Piece> initialBoard) {
        Map<JanggiPosition, Piece> savedJanggiBoard = new HashMap<>(initialBoard);
        this.janggiBoard = new JanggiBoard(savedJanggiBoard);
        return new Run(janggiBoard, new Player(Side.CHO));
    }

    @Override
    public GameState playSingleTurn(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new UnsupportedOperationException("게임을 start 해야만 move 할 수 있습니다.");
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public Map<JanggiPosition, Piece> getBoard() {
        return janggiBoard.getJanggiBoard();
    }

    @Override
    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("게임을 start 해야만 플레이어를 알 수 있습니다.");
    }

    @Override
    public int getChoScore() {
        throw new UnsupportedOperationException("게임이 끝나야만 점수를 계산할 수 있습니다.");
    }

    @Override
    public int getHanScore() {
        throw new UnsupportedOperationException("게임이 끝나야만 점수를 계산할 수 있습니다.");
    }
}
