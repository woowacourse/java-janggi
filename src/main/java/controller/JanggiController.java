package controller;

import controller.command.TurnCommand;
import dao.GameRoomRawData;
import domain.board.formation.FormationType;
import domain.game.JanggiGame;
import domain.game.Team;
import java.util.List;
import service.JanggiGameService;
import view.InputView;
import view.MainMenu;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGameService gameService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiGameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        while (true) {
            MainMenu menu = inputView.askMainMenu();
            if (menu == MainMenu.EXIT) {
                return;
            }
            handleMenu(menu);
        }
    }

    private void handleMenu(MainMenu menu) {
        if (menu == MainMenu.NEW_GAME) {
            createAndPlayGame();
            return;
        }
        if (menu == MainMenu.LOAD_GAME) {
            listAndEnterRoom();
        }
    }

    private void createAndPlayGame() {
        String roomName = inputView.askRoomName();
        FormationType choFormation = FormationConverter.convert(inputView.initialFormation(Team.CHO));
        FormationType hanFormation = FormationConverter.convert(inputView.initialFormation(Team.HAN));
        JanggiGame game = gameService.createGame(roomName, choFormation, hanFormation);
        outputView.printGameCreated(game.getId());
        playGame(game);
    }

    private void listAndEnterRoom() {
        List<GameRoomRawData> rooms = gameService.listRooms();
        outputView.printRoomList(rooms);
        if (rooms.isEmpty()) {
            return;
        }
        long roomId = inputView.askRoomId();
        gameService.enterGame(roomId)
                .ifPresentOrElse(
                        this::playGame,
                        () -> outputView.printError("존재하지 않는 방입니다.")
                );
    }

    private void playGame(JanggiGame game) {
        outputView.printBoard(game.getBoard());
        while (game.isRunning()) {
            executeTurn(game);
            outputView.printBoard(game.getBoard());
        }
        outputView.printResult(game.result());
    }

    private void executeTurn(JanggiGame game) {
        try {
            TurnCommand command = inputView.askTurnCommand(game.currentTurn());
            command.apply(gameService, game);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }
}
