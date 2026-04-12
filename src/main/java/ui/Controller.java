package ui;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.List;
import java.util.Map;
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
        long gameId = selectLobbyMenu();

        while (!janggiService.isFinished(gameId)) {
            printBoardStatus(gameId);
            Team currentTeam = janggiService.getCurrentTeam(gameId);
            ActionType actionType = retry(() -> inputView.readAction(currentTeam));

            if (actionType == ActionType.MOVE) {
                // TODO 연속 입력 구현 필요
                retry(() -> tryToMove(gameId, currentTeam));
            }

            if (actionType == ActionType.PASS) {
                janggiService.pass(gameId);
            }
        }
        Team team = janggiService.getWinner(gameId);
        resultView.printResult(team);
    }

    private void tryToMove(long gameId, Team currentTeam) {
        MovePositionDto movePositionDto = inputView.readMovePositions(currentTeam);
        PositionDto start = movePositionDto.getStart();
        PositionDto destination = movePositionDto.getDestination();
        janggiService.move(gameId, start.toDomain(), destination.toDomain());
    }

    private long selectLobbyMenu() {
        LobbyMenu lobbyMenu = retry(inputView::readGameRoomOption);

        if (lobbyMenu == LobbyMenu.CREATE_ROOM) {
            return createGame();
        }
        return joinGame();
    }

    private long createGame() {
        String title = inputView.readGameTitle();
        List<SettingType> settingTypes = retry(inputView::readSettings);
        return janggiService.createGame(title, settingTypes.get(0), settingTypes.get(1));
    }

    private long joinGame() {
        printGameRoomList();
        return retry(this::selectRoom);
    }

    private long selectRoom() {
        long gameId = inputView.readRoomId();
        janggiService.validateIsRoomAvailable(gameId);
        return gameId;
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

    private void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                resultView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void printGameRoomList() {
        List<GameRoomInfo> roomList = janggiService.getRoomList();
        resultView.printGameRoom(roomList);
    }

    private void printBoardStatus(long gameId) {
        Map<Position, Piece> gameStatus = janggiService.getGameStatus(gameId);
        BoardStatusDto statusDto = BoardStatusDto.from(gameStatus);

        resultView.printBoard(statusDto);
        double choScore = janggiService.getTeamScore(gameId, Team.CHO);
        double hanScore = janggiService.getTeamScore(gameId, Team.HAN);
        resultView.printScore(choScore, hanScore);
    }

//    private JanggiGame retry(Function<JanggiGame, JanggiGame> gameFunc, JanggiGame janggiGame) {
//        while (true) {
//            try {
//                return gameFunc.apply(janggiGame);
//            } catch (IllegalArgumentException e) {
//                resultView.printErrorMessage(e.getMessage());
//            }
//        }
//    }
}
