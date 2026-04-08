package janggi.controller;

import janggi.model.Janggi;
import janggi.model.board.BoardType;
import janggi.model.position.absolute.Position;
import janggi.service.JanggiService;
import janggi.service.dto.GameDetailResponse;
import janggi.service.dto.GameOptionResponse;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.GameStatus;
import java.util.List;

public class JanggiController {

    private static final long NEW_GAME_OPTION = 0L;

    private final OutputView outputView;
    private final InputView inputView;
    private final JanggiService janggiService;

    public JanggiController(
            OutputView outputView,
            InputView inputView,
            JanggiService janggiService

    ) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.janggiService = janggiService;
    }

    public void run() {
        List<GameOptionResponse> gameOptions = janggiService.loadAllGames();
        GameDetailResponse response = setUpGame(gameOptions);

        Long gameId = response.gameId();
        Janggi currentJanggi = response.janggi();

        while (janggiService.isGameContinued(currentJanggi)) {
            outputView.printGameStatus(GameStatus.from(currentJanggi));

            currentJanggi = janggiService.updateBoardWith(
                    currentJanggi,
                    readFromPosition(),
                    readToPosition()
            );

            if (!janggiService.isGameContinued(currentJanggi)) {
                break;
            }

            if (readDrawAccept()) {
                currentJanggi = janggiService.draw(currentJanggi);
                break;
            }

            if (!readIsContinued()) {
                break;
            }
        }

        if (currentJanggi.isGameOver()) {
            outputView.printWinner(currentJanggi.getWinner());
            janggiService.removeGame(gameId);
        }
    }

    private GameDetailResponse setUpGame(List<GameOptionResponse> gameOptionResponses) {
        Long chosenGameId = readChosenGameId(gameOptionResponses);

        if (NEW_GAME_OPTION == chosenGameId) {
            return initNewGame();
        }

        return janggiService.loadGameByGameId(chosenGameId);
    }

    private Long readChosenGameId(List<GameOptionResponse> gameOptionResponses) {
        if (gameOptionResponses.isEmpty()) {
            return NEW_GAME_OPTION;
        }

        outputView.printGameOptions(gameOptionResponses);
        return inputView.readGameOption();
    }

    private GameDetailResponse initNewGame() {
        BoardType boardType = readBoardType();
        return janggiService.initGame(boardType);
    }

    private BoardType readBoardType() {
        outputView.printBoardInitialTypeMessage();
        int initializeTypeValue = inputView.readBoardInitializeType();

        return janggiService.createBoardTypeOf(initializeTypeValue);
    }

    private Position readFromPosition() {
        outputView.printFromPositionMessage();
        return convertPositionInfoToPosition(
                inputView.readPosition()
        );
    }

    private Position readToPosition() {
        outputView.printToPositionMessage();
        return convertPositionInfoToPosition(
                inputView.readPosition()
        );
    }

    private Position convertPositionInfoToPosition(List<Integer> positionInfo) {
        int rowIndex = 0;
        int rowNumber = positionInfo.get(rowIndex);

        if (rowNumber == 0) {
            rowNumber = 10;
        }

        int columnIndex = 1;
        Integer columnNumber = positionInfo.get(columnIndex);

        return janggiService.createPositionOf(rowNumber, columnNumber);

    }

    private boolean readDrawAccept() {
        outputView.printInputDrawAcceptPrompt();
        return inputView.readYesOrNo();
    }

    private boolean readIsContinued() {
        outputView.printInputContinuePrompt();
        return inputView.readYesOrNo();
    }
}
