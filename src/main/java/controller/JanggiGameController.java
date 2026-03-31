package controller;

import domain.BoardStatus;
import domain.JanggiGame;
import domain.SettingType;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import view.ActionType;
import view.BoardStatusDto;
import view.InputView;
import view.PositionDto;
import view.ResultView;

public class JanggiGameController {
    private final InputView inputView;
    private final ResultView resultView;

    public JanggiGameController(InputView inputView, ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void play() {

        JanggiGame game = retry(this::initializeGame);

        //2. 게임 진행 -> 반복문.
        playTurn(game);
    }

    private void playTurn(JanggiGame game) {
        while (true) {
            ActionType actionType = retry(() -> inputView.readAction(game.getTurn()));
            if (actionType == ActionType.MOVE) {
                retry(this::executeMove, game);
            }
            if (actionType == ActionType.PASS) {
                game.passTurn();
            }
            printBoardStatus(game.getJanggiGameStatus());
        }
    }

    private void executeMove(JanggiGame game) {
        PositionDto positionDto = inputView.readMovePositions(game.getTurn());

        Position startPosition = Position.of(positionDto.getStartRow(), positionDto.getStartColumn());
        Position destinationPosition = Position.of(positionDto.getDestinationRow(),
                positionDto.getDestinationColumn());
        game.executeMove(startPosition, destinationPosition);
    }

    private JanggiGame initializeGame() {
        List<SettingType> settingTypes = inputView.readSettings();
        SettingType choSettingType = settingTypes.getFirst();
        SettingType hanSettingType = settingTypes.getLast();

        JanggiGame game = JanggiGame.init(choSettingType, hanSettingType);
        printBoardStatus(game.getJanggiGameStatus());
        return game;
    }

    private void printBoardStatus(BoardStatus status) {
        Map<Position, Piece> boardStatus = status.boardStatus();
        BoardStatusDto statusDto = BoardStatusDto.from(boardStatus);

        resultView.printBoard(statusDto);
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                resultView.printRetryDescription(e);
            }
        }
    }

    private void retry(Consumer<JanggiGame> consumer, JanggiGame janggiGame) {
        while (true) {
            try {
                consumer.accept(janggiGame);
                return;
            } catch (IllegalArgumentException e) {
                resultView.printRetryDescription(e);
            }
        }
    }
}
