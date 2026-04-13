package view;

import application.GameStartResult;
import domain.board.Board;
import domain.game.GameResult;
import domain.game.GameScore;
import domain.pieces.Side;
import view.mapper.BoardViewMapper;
import view.mapper.GameResultViewMapper;
import view.mapper.GameScoreResultViewMapper;

public class GamePresenter {
    private final OutputView outputView;
    private final BoardViewMapper boardViewMapper = new BoardViewMapper();
    private final GameResultViewMapper gameResultViewMapper = new GameResultViewMapper();
    private final GameScoreResultViewMapper gameScoreResultViewMapper = new GameScoreResultViewMapper();

    public GamePresenter(OutputView outputView) {
        this.outputView = outputView;
    }

    public void printSangSetupType(Side side) {
        outputView.printSangSetupType(side);
    }

    public void printTurn(Side side) {
        outputView.printTurn(side);
    }

    public void printMoveGuide() {
        outputView.printMoveGuide();
    }

    public void printErrorMessage(String message) {
        outputView.printErrorMessage(message);
    }

    public void printMessage(String message) {
        outputView.printMessage(message);
    }

    public void printStartMessage(GameStartResult startResult) {
        if (startResult.resumed()) {
            outputView.printMessage("저장된 진행 중 게임을 불러왔습니다.");
            return;
        }
        outputView.printMessage("저장된 진행 중 게임이 없습니다. 새 게임을 시작합니다.");
    }

    public void printPausedMessage() {
        outputView.printMessage("현재 진행 중인 게임을 저장된 상태로 종료합니다.");
    }

    public void printBoard(Board board) {
        outputView.printBoard(boardViewMapper.map(board));
    }

    public void printGameResult(GameResult gameResult) {
        outputView.printGameResult(gameResultViewMapper.map(gameResult));
    }

    public void printGameScoreResult(GameResult gameResult, GameScore gameScore) {
        outputView.printGameScoreResult(gameScoreResultViewMapper.map(gameResult, gameScore));
    }
}
