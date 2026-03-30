package ui;

import domain.JanggiGame;
import domain.piece.Piece;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import ui.dto.ActionType;
import ui.dto.BoardStatusDto;
import ui.dto.PositionDto;
import ui.view.InputView;
import ui.view.ResultView;

public class Controller {
    private final InputView inputView;
    private final ResultView resultView;

    public Controller(InputView inputView, ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void play() {
        JanggiGame game = retry(this::initializeGame);
        playTurn(game);
    }

    private JanggiGame initializeGame() {
        List<SettingType> settingTypes = inputView.readSettings();
        SettingType choSettingType = settingTypes.getFirst();
        SettingType hanSettingType = settingTypes.getLast();

        JanggiGame game = JanggiGame.init(choSettingType, hanSettingType);
        printBoardStatus(game.getJanggiGameStatus());
        return game;
    }

    private void playTurn(JanggiGame game) {
        while (true) {
            ActionType actionType = retry(() -> inputView.readAction(game.getTurn()));
            executeMoveIfActionIsMove(game, actionType);
            executePassTurnIfActionIsPass(game, actionType);
            printBoardStatus(game.getJanggiGameStatus());
        }
    }

    private void executeMoveIfActionIsMove(JanggiGame game, ActionType actionType) {
        if (actionType == ActionType.MOVE) {
            retry(this::executeMove, game);
        }
    }

    private void executeMove(JanggiGame game) {
        PositionDto positionDto = inputView.readMovePositions(game.getTurn());

        Position startPosition = Position.of(positionDto.getStartRow(), positionDto.getStartColumn());
        Position destinationPosition = Position.of(positionDto.getDestinationRow(), positionDto.getDestinationColumn());
        game.executeMove(startPosition, destinationPosition);
    }

    private void executePassTurnIfActionIsPass(JanggiGame game, ActionType actionType) {
        if (actionType == ActionType.PASS) {
            game.passTurn();
        }
    }

    private void printBoardStatus(Map<Position, Piece> boardStatus) {
        BoardStatusDto statusDto = BoardStatusDto.from(boardStatus);

        resultView.printBoard(statusDto);
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                resultView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void retry(Consumer<JanggiGame> consumer, JanggiGame janggiGame) {
        while (true) {
            try {
                consumer.accept(janggiGame);
                return;
            } catch (IllegalArgumentException e) {
                resultView.printErrorMessage(e.getMessage());
            }
        }
    }
}
