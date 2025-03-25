package manager;

import domain.board.DefaultBoardFactory;
import domain.piece.character.Team;
import service.GameService;
import util.ErrorHandler;
import view.Command;
import view.InputView;
import view.MoveCommand;
import view.OutputView;

public class GameManager {

    private final GameService gameService;

    public GameManager(GameService gameService) {
        this.gameService = gameService;
    }

    public void startGame() {
        ErrorHandler.catchException(this::loadGameRoom);

        while (gameService.isPlaying()) {
            Command command = InputView.inputCommand();
            if (command.isEnd()) {
                ErrorHandler.catchException(this::processEnd);
                return;
            }

            if (command.isMove()) {
                ErrorHandler.catchException(this::processMove);
                continue;
            }

            if (command.isStatus()) {
                ErrorHandler.catchException(this::processStatus);
            }
        }
    }

    private void processEnd() {
        OutputView.printStatus(gameService.calculateScore(Team.CHO), gameService.calculateScore(Team.HAN));
        OutputView.printMatchResult(gameService.findWinTeam());
        gameService.endGame();
    }

    private void processMove() {
        OutputView.printPieceByPoint(gameService.findPieceByPoint());

        MoveCommand moveCommand = InputView.inputMoveCommand(gameService.currentTurn());
        gameService.movePiece(moveCommand.source(), moveCommand.destination());
        OutputView.printPieceByPoint(gameService.findPieceByPoint());
    }

    private void processStatus() {
        OutputView.printStatus(gameService.calculateScore(Team.CHO), gameService.calculateScore(Team.HAN));
    }

    private void loadGameRoom() {
        ErrorHandler.retryUntilSuccess(() -> {
            String gameRoomName = InputView.inputGameRoomName();
            setUpGame(gameRoomName);
            OutputView.printStart(gameRoomName);
        });
    }

    private void setUpGame(String gameRoomName) {
        if (gameService.existsGameRoom(gameRoomName)) {
            OutputView.printLoadingSavedGame(gameRoomName);
            gameService.loadGame(gameRoomName);
            return;
        }

        OutputView.printCreatingNewGame(gameRoomName);
        gameService.createNewGame(gameRoomName, DefaultBoardFactory.getInstance(),
                InputView.inputSangMaOrder(Team.CHO),
                InputView.inputSangMaOrder(Team.HAN));
    }
}
