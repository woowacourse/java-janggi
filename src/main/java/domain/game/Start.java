package domain.game;

import domain.JanggiBoard;
import domain.JanggiBoardFactory;
import domain.JanggiPosition;
import domain.Score;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.Map;

public class Start implements GameState {
    JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

    @Override
    public GameState start() {
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
    public Score getScore() {
        throw new UnsupportedOperationException("게임이 끝나야만 점수를 계산할 수 있습니다.");
    }
}
