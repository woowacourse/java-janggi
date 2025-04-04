package domain.game;

import static view.InputView.inputGameId;
import static view.InputView.inputNewGameId;

import dao.JanggiPieceEntity;
import domain.JanggiBoard;
import domain.JanggiBoardFactory;
import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.List;
import java.util.Map;
import service.JanggiService;

public class Start implements GameState {
    private JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());
    protected final JanggiService janggiService;

    public Start(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    @Override
    public GameState start() {
        Long gameId = inputGameId();
        if (gameId == -1) {
            gameId = inputNewGameId();
            janggiService.createJanggiGame(gameId);
        }

        List<JanggiPieceEntity> pieceEntities = janggiService.getJanggiPieces(gameId);
        Map<JanggiPosition, Piece> savedJanggiBoard = janggiService.loadJanggiBoard(pieceEntities);
        if (!savedJanggiBoard.isEmpty()) {
            this.janggiBoard = new JanggiBoard(savedJanggiBoard);
        }
        return new Run(gameId, janggiService, janggiBoard, new Player(Side.CHO));
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
