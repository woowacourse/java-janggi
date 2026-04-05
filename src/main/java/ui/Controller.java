package ui;

import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import domain.state.JanggiGame;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import repository.GameRoomInfo;
import service.JanggiService;
import ui.dto.ActionType;
import ui.dto.BoardStatusDto;
import ui.dto.MovePositionDto;
import ui.dto.PositionDto;
import ui.view.InputView;
import ui.view.InputView.LobbyMenu;
import ui.view.ResultView;

public class Controller {
    private final InputView inputView;
    private final ResultView resultView;
    private final JanggiService janggiService;

    public Controller(InputView inputView, ResultView resultView, JanggiService janggiService) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.janggiService = janggiService;
    }

    public void play() {
        JanggiGame game = selectLobbyMenu();
        game = playTurn(game);
        judgeResult(game);
    }

    private JanggiGame selectLobbyMenu() {
        LobbyMenu lobbyMenu = retry(inputView::readGameRoomOption);

        if (lobbyMenu == LobbyMenu.CREATE_ROOM) {
            return createGame();
        }
        return joinGame();
    }

    private JanggiGame createGame() {
        String title = inputView.readGameTitle();
        List<SettingType> settingTypes = retry(inputView::readSettings);
        return janggiService.createGame(title, settingTypes.get(0), settingTypes.get(1));
    }

    private JanggiGame joinGame() {
        printGameRoomList();
        return retry(this::findRoom);
    }

    private void printGameRoomList() {
        List<GameRoomInfo> roomList = janggiService.getRoomList();
        resultView.printGameRoom(roomList);
    }

    private JanggiGame findRoom() {
        long id = inputView.readRoomId();
        return janggiService.joinGame(id);
    }

    private JanggiGame playTurn(JanggiGame game) {
        while (!game.isFinished()) {
            printBoardStatus(game);
            game = actByActionType(game);
        }
        return game;
    }

    private void printBoardStatus(JanggiGame game) {
        BoardStatusDto statusDto = BoardStatusDto.from(game.getBoard());

        resultView.printBoard(statusDto);
        resultView.printScore(game.getScoreByTeam(Team.CHO), game.getScoreByTeam(Team.HAN));
    }

    private JanggiGame actByActionType(JanggiGame game) {
        Team currentTeam = game.getTurn();
        ActionType actionType = retry(() -> inputView.readAction(currentTeam));

        if (actionType == ActionType.MOVE) {
            return retry(this::executeMove, game);
        }
        return janggiService.pass(game);
    }

    private JanggiGame executeMove(JanggiGame game) {
        MovePositionDto movePositionDto = inputView.readMovePositions(game.getTurn());

        PositionDto start = movePositionDto.getStart();
        PositionDto destination = movePositionDto.getDestination();

        Position startPosition = Position.of(start.getRow(), start.getColumn());
        Position destinationPosition = Position.of(destination.getRow(), destination.getColumn());

        return janggiService.move(game, startPosition, destinationPosition);
    }

    private void judgeResult(JanggiGame game) {
        janggiService.endGame(game);
        resultView.printResult(game.judgeWinner());
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
