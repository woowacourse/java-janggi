package janggi;

import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.repository.GameRepository;
import janggi.repository.LoadedGame;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.CampDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameRunner {
    private static final String INVALID_GAME_ROOM = "[ERROR] 존재하지 않는 게임방 번호입니다.";

    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public GameRunner(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void run() {
        LoadedGame loadedGame = retryOnInvalidInput(this::loadOrCreateGame);
        long gameId = loadedGame.id();
        Game game = loadedGame.game();

        outputView.printBoard(game.boardSnapshot());
        play(gameId, game);
    }

    private LoadedGame loadOrCreateGame() {
        outputView.printExistGameRoom(gameRepository.findAllIds());
        long gameId = inputView.readSelectedGameRoom();

        if (gameId == 0L) {
            return createNewGame();
        }

        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_GAME_ROOM));
    }

    private LoadedGame createNewGame() {
        Board board = createBoard();
        return gameRepository.create(board);
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

    private void play(long id, Game game) {
        boolean continueGame = true;
        while (continueGame) {
            outputView.printScore(game.calculateScore());
            continueGame = retryOnInvalidInput(() -> playTurn(id, game));
            outputView.printBoard(game.boardSnapshot());
        }
        outputView.printWinner(game.currentTurn());
    }

    private boolean playTurn(long id, Game game) {
        Camp currentTurn = game.currentTurn();

        Position source = retryOnInvalidInput(() -> readSource(game, currentTurn));
        Position destination = retryOnInvalidInput(inputView::readDestination);

        boolean gameEnded = game.play(source, destination);
        if (gameEnded) {
            gameRepository.deleteById(id);
            return false;
        }
        gameRepository.update(id, game);
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
