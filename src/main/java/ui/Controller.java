package ui;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import domain.state.JanggiGame;
import domain.state.Playing;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import ui.dto.ActionType;
import ui.dto.BoardStatusDto;
import ui.dto.MovePositionDto;
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

    // 게임 시작
    public void play() {
        // TODO: 방 입장 or  방 만들기 옵션 구현
        JanggiGame game = retry(this::initializeGame);

        game = playTurn(game);
        resultView.printResult(game.judgeWinner());
    }

    private JanggiGame initializeGame() {
        List<SettingType> settingTypes = inputView.readSettings();
        SettingType choSettingType = settingTypes.getFirst();
        SettingType hanSettingType = settingTypes.getLast();

        JanggiGame game = Playing.init(choSettingType, hanSettingType);
        printBoardStatus(game.getBoard());
        return game;
    }

    private JanggiGame playTurn(JanggiGame game) {
        while (!game.isFinished()) {
            resultView.printScore(game.getScoreByTeam(Team.CHO), game.getScoreByTeam(Team.HAN));
            Team currentTeam = game.getTurn();
            ActionType actionType = retry(() -> inputView.readAction(currentTeam));

            game = executeMove(game, actionType);
            game = pass(game, actionType);
            printBoardStatus(game.getBoard());
        }
        return game;
    }

    private static JanggiGame pass(JanggiGame game, ActionType actionType) {
        if (actionType == ActionType.PASS) {
            game = game.pass();
        }
        return game;
    }

    private JanggiGame executeMove(JanggiGame game, ActionType actionType) {
        if (actionType == ActionType.MOVE) {
            game = retry(this::executeMove, game);
        }
        return game;
    }

    private JanggiGame executeMove(JanggiGame game) {
        MovePositionDto movePositionDto = inputView.readMovePositions(game.getTurn());

        PositionDto start = movePositionDto.getStart();
        PositionDto destination = movePositionDto.getDestination();

        Position startPosition = Position.of(start.getRow(), start.getColumn());
        Position destinationPosition = Position.of(destination.getRow(), destination.getColumn());
        return game.move(startPosition, destinationPosition);
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

    private JanggiGame retry(Function<JanggiGame, JanggiGame> gameFunc, JanggiGame janggiGame) {
        while (true) {
            try {
                return gameFunc.apply(janggiGame);
            } catch (IllegalArgumentException e) {
                resultView.printErrorMessage(e.getMessage());
            }
        }
    }
}
