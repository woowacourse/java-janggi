package controller;

import domain.Position;
import dto.SelectPositionRequest;
import exception.JanggiGameException;
import service.JanggiCommandService;
import service.JanggiQueryService;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final JanggiCommandService commandService;
    private final JanggiQueryService queryService;

    public JanggiController(JanggiCommandService commandService, JanggiQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    public void run() {
        runSetupGamePhase();
        runPlayingPhase();
        runResultPhase();
    }

    private void runSetupGamePhase() {
        commandService.setupGame();
    }

    private void runPlayingPhase() {
        while (queryService.isInProgress()) {
            displayCurrentGameState();
            execute(this::movePiece);
        }
    }

    private void displayCurrentGameState() {
        OutputView.printBoard(queryService.allFactors());
        OutputView.printCurrentPlayerTurn(queryService.currentPlayerTurn());
    }

    private void movePiece() {
        Position selected = selectPiecePositionToMove();
        Position target = selectPiecePositionToGoFrom(selected);

        commandService.move(selected, target);
    }

    private Position selectPiecePositionToMove() {
        SelectPositionRequest selectRequest = InputView.selectPiecePosition();
        return Position.of(selectRequest.row(), selectRequest.col());
    }

    private Position selectPiecePositionToGoFrom(Position selected) {
        SelectPositionRequest targetRequest =
                InputView.selectTargetPositionWith(queryService.findPieceInfoAt(selected));

        return Position.of(targetRequest.row(), targetRequest.col());
    }

    private void runResultPhase() {
        OutputView.printBoard(queryService.allFactors());
        OutputView.printResult(queryService.gameStatus());
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
