package janggi;

import janggi.domain.Arrangement;
import janggi.domain.Game;
import janggi.domain.Position;
import janggi.dto.TurnDto;
import janggi.service.GamePersistenceService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Runner {
    private static final long CREATE_NEW_GAME = -1L;

    private final GamePersistenceService gamePersistenceService;

    public Runner(GamePersistenceService gamePersistenceService) {
        this.gamePersistenceService = gamePersistenceService;
    }

    public void run() {
        gamePersistenceService.initialize();
        startGame();
    }

    private void startGame() {
        while (true) {
            try {
                long roomId = InputView.askRoomId();
                if (roomId == CREATE_NEW_GAME) {
                    runNewGame();
                    return;
                }

                Game game = gamePersistenceService.enterGame(roomId);
                turnGame(roomId, game);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void runNewGame() {
        String hanArrangementInput = InputView.askHanArrangement();
        Arrangement hanArrangement = Arrangement.from(hanArrangementInput);

        String choArrangementInput = InputView.askChoArrangement();
        Arrangement choArrangement = Arrangement.from(choArrangementInput);

        Game game = new Game(choArrangement, hanArrangement);
        long roomId = gamePersistenceService.saveNewGame(game);
        OutputView.printNewGameRoom(roomId);

        turnGame(roomId, game);
    }

    private void turnGame(long roomId, Game game) {
        while (!game.isFinished()) {
            playTurnGame(roomId, game);
        }
        printWinner(game);
    }

    private void playTurnGame(long roomId, Game game) {
        try {
            printCurrentStatus(game, roomId);
            Position startPosition = Position.from(InputView.askStartPosition());
            Position endPosition = Position.from(InputView.askEndPosition());

            game.move(startPosition, endPosition);

            gamePersistenceService.saveMove(
                    roomId,
                    game,
                    new TurnDto(
                            startPosition.x(),
                            startPosition.y(),
                            endPosition.x(),
                            endPosition.y()
                    )
            );
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
        } catch (IllegalStateException e) {
            OutputView.printErrorMessage(e.getMessage());
            throw e;
        }
    }

    private void printCurrentStatus(Game game, long roomId) {
        OutputView.printGameRoomNumber(roomId);
        OutputView.printBoard(game.getCurrentBoard());
        OutputView.printTurn(game.getCurrentSide());
        OutputView.printScoreStatus(game.getCurrentScoreStatus());
    }

    private void printWinner(Game game) {
        OutputView.printBoard(game.getCurrentBoard());
        OutputView.printWinner(game.getCurrentSide());
    }
}
