import domain.*;
import repository.CounterRepository;
import repository.GameRepository;
import view.ConsoleView;

import java.util.List;
import java.util.function.Supplier;

import static constant.ErrorMessage.GAME_NOT_FOUND;

public class JanggiRunner {

    private final ConsoleView consoleView;
    private final GameRepository gameRepository;
    private final CounterRepository counterRepository;

    public JanggiRunner(ConsoleView consoleView, GameRepository gameRepository, CounterRepository counterRepository) {
        this.consoleView = consoleView;
        this.gameRepository = gameRepository;
        this.counterRepository = counterRepository;
    }

    public void run() {
        Game game = loadGame();
        playGame(game);
    }

    private Game loadGame() {
        return retryOnError(this::selectGame);
    }

    private Game selectGame() {
        int option = readOption();
        if (option == 1) {
            return createNewGame();
        }
        return loadExistingGame();
    }

    private Game createNewGame() {
        Game game = new Game(counterRepository.getNextId(), new Turn(Side.CHO), readChoFormation(), readHanFormation());
        gameRepository.saveGame(game);
        consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
        return game;
    }

    private Game loadExistingGame() {
        Game game = gameRepository.findBoard(consoleView.readGameId())
                .orElseThrow(() -> new IllegalArgumentException(GAME_NOT_FOUND))
                .toGame();
        consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
        return game;
    }

    private int readOption() {
        return retryOnError(consoleView::readOption);
    }

    private void playGame(Game game) {
        boolean isRunning = true;
        while (isRunning) {
            consoleView.printCurrentTurn(game.getTurn());
            TurnAction action = readTurnAction();
            isRunning = executeAction(game.getTurn(), game, action);
        }
    }

    private boolean executeAction(Turn turn, Game game, TurnAction action) {
        if (action == TurnAction.PASS) {
            turn.next();
            consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
            return true;
        }
        if (action == TurnAction.JUDGE) {
            consoleView.printResultByScore(game.calculateScore());
            gameRepository.deleteBoard(game.getId());
            return false;
        }
        return move(game, turn);
    }

    private boolean move(Game game, Turn turn) {
        return retryOnError(() -> {
            Position sourcePosition = readSourcePosition();
            List<Position> availableTargets = findAvailableTarget(game, turn, sourcePosition);
            Position targetPosition = readTargetPosition();
            moveToTarget(game, sourcePosition, targetPosition, availableTargets);
            return handleMoveResult(game, turn);
        });
    }

    private boolean handleMoveResult(Game game, Turn turn) {
        if (game.isFinished()) {
            consoleView.printWinner(turn.current());
            gameRepository.deleteBoard(game.getId());
            return false;
        }
        turn.next();
        afterMove(game);
        return true;
    }

    private List<Position> findAvailableTarget(Game game, Turn turn, Position sourcePosition) {
        List<Position> availableTargets = game.findPath(sourcePosition, turn);
        consoleView.printAvailablePath(game.getId(), availableTargets, game.getBoard(), game.calculateScore());
        return availableTargets;
    }

    private void moveToTarget(Game game, Position sourcePosition, Position targetPosition, List<Position> availableTargets) {
        game.movePiece(sourcePosition, targetPosition, availableTargets);
        consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
    }

    private void afterMove(Game game) {
        gameRepository.saveGame(game);
    }

    private Position readSourcePosition() {
        return retryOnError(consoleView::readSourcePosition);
    }

    private Position readTargetPosition() {
        return retryOnError(consoleView::readTargetPosition);
    }

    private Formation readChoFormation() {
        return retryOnError(consoleView::readChoFormation);
    }

    private Formation readHanFormation() {
        return retryOnError(consoleView::readHanFormation);
    }

    private TurnAction readTurnAction() {
        return retryOnError(consoleView::readTurnAction);
    }

    private <T> T retryOnError(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                consoleView.printErrorMessage(e.getMessage());
            }
        }
    }
}
