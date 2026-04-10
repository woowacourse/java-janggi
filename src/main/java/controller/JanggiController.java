package controller;

import dao.GameRoomRawData;
import domain.board.formation.FormationType;
import domain.game.JanggiGame;
import domain.game.Team;
import domain.position.Position;
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
        try {
            if (menu == MainMenu.NEW_GAME) {
                createAndPlayGame();
                return;
            }
            if (menu == MainMenu.LOAD_GAME) {
                listAndEnterRoom();
            }
        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
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
        playGame(gameService.enterGame(roomId));
    }

    private void playGame(JanggiGame game) {
        outputView.printBoard(game.getBoard());
        while (game.isRunning()) {
            playTurn(game);
            outputView.printBoard(game.getBoard());
        }
        outputView.printResult(game.findWinner(), game.scoreOf(Team.CHO), game.scoreOf(Team.HAN));
    }

    private void playTurn(JanggiGame game) {
        boolean isTurnCompleted = false;
        while (!isTurnCompleted) {
            isTurnCompleted = executeTurn(game);
        }
    }

    private boolean executeTurn(JanggiGame game) {
        try {
            String input = inputView.askTurnInput(game.currentTurn());
            if (inputView.isPass(input)) {
                gameService.pass(game);
                return true;
            }
            List<Position> positions = inputView.parseMoveInput(input);
            gameService.move(game, positions.get(0), positions.get(1));
            return true;
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return false;
        }
    }
}
