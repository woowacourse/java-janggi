package janggi;

import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.CampDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiGame {

    private static final long SINGLE_GAME_ID = 1L;

    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;

    public JanggiGame(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        Game game = loadOrCreateGame();
        outputView.printBoard(game.boardSnapshot());
        play(game);
    }

    private Game loadOrCreateGame() {
        return gameService.findById(SINGLE_GAME_ID)
                .orElseGet(this::createNewGame);
    }

    private Game createNewGame() {
        Board board = createBoard();
        Game game = Game.start(SINGLE_GAME_ID, board);
        gameService.save(game);
        return game;
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

    private void play(Game game) {
        boolean continueGame = true;
        while (continueGame) {
            outputView.printScore(game.calculateScore());
            continueGame = retryOnInvalidInput(() -> playTurn(game));
            outputView.printBoard(game.boardSnapshot());
        }
        outputView.printWinner(game.currentTurn());
    }

    private boolean playTurn(Game game) {
        Camp currentTurn = game.currentTurn();

        Position source = retryOnInvalidInput(() -> readSource(game, currentTurn));
        Position destination = retryOnInvalidInput(inputView::readDestination);

        boolean gameEnded = game.play(source, destination);
        if (gameEnded) {
            gameService.deleteById(game.id());
            return false;
        }
        gameService.save(game);
        return true;
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
