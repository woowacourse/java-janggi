package controller;

import domain.Position;
import dto.SelectResumeOptionRequest;
import dto.SelectPositionRequest;
import exception.JanggiGameException;
import java.util.function.Supplier;
import service.JanggiCommandService;
import service.JanggiQueryService;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final JanggiCommandService commandService;
    private final JanggiQueryService queryService;

    private long currentGameId = 0L;

    public JanggiController(JanggiCommandService commandService, JanggiQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    public void run() {
        if(executeWithReturn(this::isPlayerWantPlayingUnfinishedGame)) {
            runSavedGame();
            return;
        }
        runNewGame();
    }

    private void runSavedGame() {
        execute(this::setupSavedGameId);
        runGame();
    }

    private void setupSavedGameId() {
        long gameId = InputView.selectSavedGameId(queryService.findUnfinishedGameInfos());
        queryService.isInProgress(gameId);
        currentGameId = gameId;
    }

    private boolean isPlayerWantPlayingUnfinishedGame() {
        if(queryService.hasUnfinishedGameId()) {
            SelectResumeOptionRequest request = InputView.selectLoadGameOrNewGame();
            return request.select();
        }
        return false;
    }

    private void runNewGame() {
        setupNewGameId();
        runGame();
    }

    private void runGame() {
        runPlayingPhase();
        runResultPhase();
    }

    private void setupNewGameId() {
        currentGameId = commandService.setupGame();
    }

    private void runPlayingPhase() {
        while (queryService.isInProgress(currentGameId)) {
            displayCurrentGameState();
            execute(this::movePiece);
        }
    }

    private void displayCurrentGameState() {
        OutputView.printBoard(queryService.allFactors(currentGameId));
        OutputView.printCurrentPlayerTurn(queryService.currentPlayerTurn(currentGameId));
    }

    private void movePiece() {
        Position selected = selectPiecePositionToMove();
        Position target = selectPiecePositionToGoFrom(selected);

        commandService.move(currentGameId, selected, target);
    }

    private Position selectPiecePositionToMove() {
        SelectPositionRequest selectRequest = InputView.selectPiecePosition();
        return Position.of(selectRequest.row(), selectRequest.col());
    }

    private Position selectPiecePositionToGoFrom(Position selected) {
        SelectPositionRequest targetRequest =
                InputView.selectTargetPositionWith(queryService.findPieceInfoAt(currentGameId, selected));

        return Position.of(targetRequest.row(), targetRequest.col());
    }

    private void runResultPhase() {
        OutputView.printBoard(queryService.allFactors(currentGameId));
        OutputView.printResult(queryService.gameStatus(currentGameId));
    }

    private void execute(ExecutableTask task) {
        while (true) {
            try {
                task.execute();
                OutputView.printTaskDivider();
                return;
            } catch (JanggiGameException e) {
                OutputView.printError(e.getMessage());
                OutputView.printTaskDivider();
            }
        }
    }

    private <T> T executeWithReturn(Supplier<T> task) {
        while (true) {
            try {
                T result = task.get(); // task 실행 후 결과값 받기
                OutputView.printTaskDivider();
                return result;         // 결과값 반환
            } catch (JanggiGameException e) {
                OutputView.printError(e.getMessage());
                OutputView.printTaskDivider();
            }
        }
    }
}
