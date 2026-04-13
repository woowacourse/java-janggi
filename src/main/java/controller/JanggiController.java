package controller;

import controller.command.TurnCommand;
import domain.board.formation.FormationType;
import domain.game.Team;
import java.util.List;
import repository.GameRoomSummary;
import repository.StoredGame;
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
        StoredGame stored = gameService.createGame(roomName, choFormation, hanFormation);
        outputView.printGameCreated(stored.id());
        playGame(stored);
    }

    private void listAndEnterRoom() {
        List<GameRoomSummary> rooms = gameService.listRooms();
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

    private void playGame(StoredGame stored) {
        outputView.printBoard(stored.game().getBoard());
        while (stored.game().isRunning()) {
            executeTurn(stored);
            outputView.printBoard(stored.game().getBoard());
        }
        outputView.printResult(stored.game().result());
    }

    private void executeTurn(StoredGame stored) {
        try {
            TurnCommand command = inputView.askTurnCommand(stored.game().currentTurn());
            command.apply(gameService, stored);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }
}
