package janggi;

import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.service.GameService;
import janggi.service.LoadedGame;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.CampDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;

    public GameRunner(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        LoadedGame loadedGame = retryOnInvalidInput(this::loadOrCreateGame);
        Game game = loadedGame.game();

        outputView.printBoard(game.boardSnapshot());
        play(loadedGame);
    }

    private LoadedGame loadOrCreateGame() {
        outputView.printExistGameRoom(gameService.getAllIds());
        long gameId = inputView.readSelectedGameRoom();

        if (gameId == 0L) {
            return createNewGame();
        }

        return gameService.loadGame(gameId);
    }

    private LoadedGame createNewGame() {
        Board board = createBoard();
        return gameService.createNewGame(board);
    }

    private Board createBoard() {
        Map<Camp, ElephantSetUp> elephantSetUps = new HashMap<>();
        readElephantSetUp(elephantSetUps, Camp.HAN);
        readElephantSetUp(elephantSetUps, Camp.CHO);

        BoardInitializer initializer = new StandardBoardInitializer(elephantSetUps);
        return new Board(initializer);
    }

    private void readElephantSetUp(Map<Camp, ElephantSetUp> elephantSetUps, Camp camp) {
        ElephantSetUp elephantSetUp = retryOnInvalidInput(
                () -> inputView.readElephantSetting(CampDto.from(camp))
        );
        elephantSetUps.put(camp, elephantSetUp);
    }

    private void play(LoadedGame loadedGame) {
        boolean continueGame = true;
        Game game = loadedGame.game();

        while (continueGame) {
            outputView.printScore(game.calculateScore());
            continueGame = retryOnInvalidInput(() -> playTurn(loadedGame));
            outputView.printBoard(game.boardSnapshot());
        }
        outputView.printWinner(game.currentTurn());
    }

    private boolean playTurn(LoadedGame loadedGame) {
        Game game = loadedGame.game();
        Camp currentTurn = game.currentTurn();

        Position source = retryOnInvalidInput(() -> readSource(game, currentTurn));
        Position destination = retryOnInvalidInput(inputView::readDestination);

        return gameService.playEachTurn(loadedGame, source, destination);
    }

    private Position readSource(Game game, Camp currentTurn) {
        Position source = inputView.readSource(CampDto.from(currentTurn));
        game.validateSourceForCurrentTurn(source);
        return source;
    }

    private <T> T retryOnInvalidInput(Supplier<T> input) {
        while (true) {
            try {
                return input.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
