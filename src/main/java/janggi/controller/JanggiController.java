package janggi.controller;

import janggi.model.Janggi;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.service.JanggiService;
import janggi.service.dto.LatestInProgressGameResponse;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.GameStatus;
import janggi.view.mapping.BoardType;
import java.util.List;
import java.util.Optional;

public class JanggiController {

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
        LatestInProgressGameResponse response = setUpBoard();

        Long gameId = response.gameId();
        Janggi currentJanggi = response.janggi();

        while (!currentJanggi.isGameOver()) {
            outputView.printGameStatus(GameStatus.from(currentJanggi));
            Position from = readPosition();
            Position to = readPosition();

            currentJanggi = janggiService.updateBoardWith(
                    currentJanggi,
                    from,
                    to
            );

            if (currentJanggi.isGameOver()) {
                break;
            }

            if (readDrawAccept()) {
                currentJanggi = currentJanggi.draw();
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

    private LatestInProgressGameResponse setUpBoard() {
        Optional<LatestInProgressGameResponse> responseOpt =
                janggiService.loadGame();

        if (responseOpt.isPresent()) {
            return responseOpt.get();
        }

        BoardType boardType = readBoardType();
        return janggiService.initGame(
                boardType.getBoard()
        );
    }

    private BoardType readBoardType() {
        outputView.printBoardInitialTypeMessage();
        return inputView.readBoardInitializeType();
    }

    private Position readPosition() {
        outputView.printFromPositionMessage();
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

        Row row = Row.of(rowNumber);
        Column column = Column.of(columnNumber);

        return new Position(row, column);
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
