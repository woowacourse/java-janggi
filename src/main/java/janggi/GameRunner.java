package janggi;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.service.GameService;
import janggi.service.GameStatus;
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
        long gameId = retryOnInvalidInput(this::loadOrCreateGame);
        outputView.printBoard(gameService.getGameStatus(gameId).boardSnapshot());
        play(gameId);
    }

    private long loadOrCreateGame() {
        outputView.printExistGameRoom(gameService.getAllIds());
        long gameId = inputView.readSelectedGameRoom();

        if (gameId == 0L) {
            return createNewGame();
        }

        gameService.validateGameExists(gameId);
        return gameId;
    }

    private long createNewGame() {
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

    private void play(long gameId) {
        boolean continueGame = true;

        while (continueGame) {
            GameStatus beforeStatus = gameService.getGameStatus(gameId);
            outputView.printScore(beforeStatus.score());

            GameStatus afterStatus = retryOnInvalidInput(() -> playTurn(gameId, beforeStatus.currentTurn()));
            outputView.printBoard(afterStatus.boardSnapshot());

            if (afterStatus.gameEnded()) {
                outputView.printWinner(afterStatus.currentTurn());
                continueGame = false;
            }
        }
    }

    private GameStatus playTurn(long gameId, Camp currentTurn) {
        Position source = retryOnInvalidInput(() -> readSource(gameId, currentTurn));
        Position destination = retryOnInvalidInput(inputView::readDestination);

        return gameService.playEachTurn(gameId, source, destination);
    }

    private Position readSource(long gameId, Camp currentTurn) {
        Position source = inputView.readSource(CampDto.from(currentTurn));
        gameService.validateSourceForCurrentTurn(gameId, source);
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
