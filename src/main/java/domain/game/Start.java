package domain.game;

import domain.JanggiBoard;
import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.Map;

public class Start implements GameState {
    JanggiBoard janggiBoard = new JanggiBoard();

    @Override
    public GameState start() {
        return new Run(janggiBoard, new Player());
    }

    @Override
    public GameState move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new UnsupportedOperationException("게임을 start 해야만 move 할 수 있습니다.");
    }

    @Override
    public GameState end() {
        return new End();
    }

    @Override
    public Boolean isEnd() {
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
}
