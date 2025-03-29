package domain.game;

import dao.JanggiDao;
import domain.JanggiBoard;
import domain.JanggiBoardFactory;
import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.Map;

public class Start implements GameState {
    private JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());
    private final JanggiDao janggiDao = new JanggiDao();

    @Override
    public GameState start() {
        // 초기화 하고, 만약 Map이 비어있지 않으면 이어하도록 보드 재설정
        Map<JanggiPosition, Piece> savedJanggiBoard = janggiDao.loadJanggiBoard();
        if (!savedJanggiBoard.isEmpty()) {
            this.janggiBoard = new JanggiBoard(savedJanggiBoard);
        }
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
