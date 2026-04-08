package controller;

import domain.Position;
import dto.SelectLoadGameRequest;
import dto.SelectPositionRequest;
import exception.JanggiGameException;
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

    public void runGame() {
        boolean selectLoadGame = false;

        if(queryService.hasUnfinishedGameId()) {
            SelectLoadGameRequest request = InputView.selectLoadUnfinishedGame();
            selectLoadGame = request.select();
        }

        if(selectLoadGame) {
            currentGameId = queryService.findLatestUnfinishedGameId();
            OutputView.printResult(queryService.gameStatus(currentGameId));

            run();
            return;
        }

        runSetupGamePhase();
        run();
    }

    public void run() {
        runPlayingPhase();
        runResultPhase();
    }

    private void runSetupGamePhase() {
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
}
