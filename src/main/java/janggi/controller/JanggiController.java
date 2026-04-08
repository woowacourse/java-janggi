package janggi.controller;

import janggi.model.BoardType;
import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.RetryHandler;
import java.util.List;

public class JanggiController {
    private final JanggiService janggiService;
    private final OutputView outputView;
    private final InputView inputView;

    public JanggiController(JanggiService janggiService, OutputView outputView, InputView inputView) {
        this.janggiService = janggiService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        if (!janggiService.exitsGame()) {
            outputView.printNoGameExist();
            runNewGame();
            return;
        }
        int newGameOption = 1;
        int selection = readGameSelection();
        if (selection == newGameOption) {
            runNewGame();
            return;
        }
        runExistingGame();
    }

    private void runNewGame() {
        String name = readGameName();
        BoardType boardType = readInitialBoardType();
        Janggi janggi = initializeBoard(boardType);
        Long gameId = janggiService.createGame(name, Team.CHO);
        janggi.withScore((choScore, hanScore) ->
                outputView.printScore(choScore.value(), hanScore.value()));
        runGame(janggi, gameId);
    }

    private void runExistingGame() {
        displayCurrentGames();
        RetryHandler.retryUntilSuccess(this::loadAndRunGame);
    }

    private void loadAndRunGame() {
        String name = readGameName();
        Long gameId = janggiService.findIdByName(name);
        Janggi janggi = janggiService.loadJanggiGameById(gameId);
        janggi.withScore((choScore, hanScore) ->
                outputView.printScore(choScore.value(), hanScore.value()));
        runGame(janggi, gameId);
    }

    private void displayCurrentGames() {
        List<String> gameNames = janggiService.findAllGameNames();
        outputView.printGameList(gameNames);
    }

    private void runGame(Janggi janggi, Long gameId) {
        while (!janggi.isGameOver()) {
            janggi.withBoard((board, team) ->
                    outputView.printBoard(board.snapshot(), team));
            janggi = playTurn(janggi, gameId);
        }
        janggi.withScore((choScore, hanScore) ->
                outputView.printScore(choScore.value(), hanScore.value()));
        janggiService.deleteGame(gameId);
    }

    private Janggi playTurn(Janggi janggi, Long gameId) {
        return RetryHandler.retryUntilSuccess(() -> readAndPlay(janggi, gameId));
    }

    private Janggi readAndPlay(Janggi janggi, Long gameId) {
        Position from = readFromPosition();
        Position to = readToPosition();
        Janggi movedJanggi = janggi.play(from, to);
        movedJanggi.withBoard((board, team) ->
                janggiService.save(gameId, board, team));
        return movedJanggi;
    }

    private int readGameSelection() {
        return RetryHandler.retryUntilSuccess(this::requestGameSelection);
    }

    private int requestGameSelection() {
        outputView.printGameSelectionMessage();
        return inputView.readGameSelection();
    }

    private String readGameName() {
        outputView.printGameNameMessage();
        return inputView.readGameName();
    }

    private BoardType readInitialBoardType() {
        return RetryHandler.retryUntilSuccess(this::requestInitialBoardType);
    }

    private BoardType requestInitialBoardType() {
        outputView.printBoardInitialTypeMessage();
        int boardTypeNumber = inputView.readBoardInitializeType();
        return BoardType.of(boardTypeNumber);
    }

    private Janggi initializeBoard(BoardType boardType) {
        return Janggi.of(boardType.init());
    }

    private Position readFromPosition() {
        outputView.printFromPositionMessage();
        return convertPositionInfoToPosition(inputView.readPosition());
    }

    private Position readToPosition() {
        outputView.printToPositionMessage();
        return convertPositionInfoToPosition(inputView.readPosition());
    }

    private Position convertPositionInfoToPosition(List<Integer> positionInfo) {
        int rowIndex = 0;
        int columnIndex = 1;

        Row row = Row.of(positionInfo.get(rowIndex));
        Column column = Column.of(positionInfo.get(columnIndex));

        return new Position(row, column);
    }
}
