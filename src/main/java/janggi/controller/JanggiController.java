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
        int selection = readGameSelection();

        if (selection == 1) {
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
                outputView.printScore(choScore.getValue(), hanScore.getValue()));
        runGame(janggi, gameId);
    }

    private void runExistingGame() {
        List<String> gameNames = janggiService.findAllGameNames();
        outputView.printGameList(gameNames);
        String name = readGameName();
        Janggi janggi = janggiService.loadGameByName(name);
        Long gameId = janggiService.findIdByName(name);
        janggi.withScore((choScore, hanScore) ->
                outputView.printScore(choScore.getValue(), hanScore.getValue()));
        runGame(janggi, gameId);
    }

    private void runGame(Janggi janggi, Long gameId) {
        while (!janggi.isGameOver()) {
            janggi.withBoard((board, team) ->
                    outputView.printBoard(board.render(), team.getDisplayName()));
            janggi = readAndPlay(janggi, gameId);
        }
        janggi.withScore((choScore, hanScore) ->
                outputView.printScore(choScore.getValue(), hanScore.getValue()));
    }

    private Janggi readAndPlay(Janggi janggi, Long gameId) {
        while (true) {
            try {
                Position from = readFromPosition();
                Position to = readToPosition();
                Janggi movedJanggi = janggi.play(from, to);
                movedJanggi.withBoard((board, team) ->
                        janggiService.save(gameId, board, team));
                return movedJanggi;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private int readGameSelection() {
        while (true) {
            try {
                outputView.printGameSelectionMessage();
                return inputView.readGameSelection();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String readGameName() {
        outputView.printGameNameMessage();
        return inputView.readGameName();
    }

    private BoardType readInitialBoardType() {
        while (true) {
            try {
                outputView.printBoardInitialTypeMessage();
                int boardTypeNumber = inputView.readBoardInitializeType();
                return BoardType.of(boardTypeNumber);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
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
