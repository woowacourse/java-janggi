package manager;

import domain.board.BoardGenerator;
import domain.piece.character.Team;
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
        loadGameRoom();

        ErrorHandler.retryUntilSuccess(() -> {
            while (gameService.isPlaying()) {
                Command command = InputView.inputCommand();

                if (command.isEnd()) {
                    OutputView.printStatus(gameService.calculateScore(Team.CHO), gameService.calculateScore(Team.HAN));
                    OutputView.printMatchResult(gameService.findWinTeam());
                    gameService.endGame();
                    return;
                }

                playByCommand(command);
            }
        });
    }

    private void playByCommand(Command command) {
        if (command.isMove()) {
            OutputView.printPieceByPoint(gameService.findPieceByPoint());

            MoveCommand moveCommand = InputView.inputMoveCommand(gameService.currentTurn());
            gameService.movePiece(moveCommand.source(), moveCommand.destination());
            OutputView.printPieceByPoint(gameService.findPieceByPoint());
            return;
        }

        if (command.isStatus()) {
            OutputView.printStatus(gameService.calculateScore(Team.CHO), gameService.calculateScore(Team.HAN));
        }
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
        gameService.setNewGame(gameRoomName, new BoardGenerator(),
                InputView.inputSangMaOrder(Team.CHO),
                InputView.inputSangMaOrder(Team.HAN));
    }
}
