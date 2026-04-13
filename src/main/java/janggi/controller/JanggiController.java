package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.Team;
import janggi.domain.board.PieceSetup;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.exception.DataAccessException;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class JanggiController {
    private static final Map<Command, Consumer<JanggiController>> COMMAND_ACTIONS = Map.of(
            Command.CONTINUE, JanggiController::continueGame,
            Command.NEW, controller -> {
                long gameId = controller.createGame();
                controller.playGame(gameId);
            },
            Command.DELETE, JanggiController::deleteGame
    );
    private static final String QUIT_COMMAND = "quit";
    private static final int FROM_INDEX = 0;
    private static final int TO_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService service;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
    }

    public void run() {
        while (true) {
            try {
                outputView.printGameList(service.findAllGames());
                Command command = inputView.readCommand();
                if (command == Command.EXIT) {
                    return;
                }
                handle(command);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void handle(Command command) {
        COMMAND_ACTIONS.get(command).accept(this);
    }

    private void continueGame() {
        while (true) {
            try {
                long gameId = inputView.readContinueGameId();
                if (gameId == 0) {
                    return;
                }
                playGame(gameId);
                return;
            } catch (IllegalArgumentException | DataAccessException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private long createGame() {
        while (true) {
            try {
                String hanSetup = inputView.readHanSetup();
                String choSetup = inputView.readChoSetup();
                return service.createGame(PieceSetup.from(hanSetup), PieceSetup.from(choSetup));
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void deleteGame() {
        while (true) {
            try {
                long gameId = inputView.readDeleteGameId();
                if (gameId == 0) {
                    return;
                }
                service.deleteGame(gameId);
                return;
            } catch (IllegalArgumentException | DataAccessException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void playGame(long gameId) {
        while (true) {
            JanggiGame game = service.loadGame(gameId);
            if (game.isFinished()) {
                printFinishedGame(game);
                return;
            }
            try {
                printBoardAndScore(game);
                List<String> positions = readPosition(game.getCurrentTeam());
                if (isQuitCommand(positions)) {
                    outputView.printGameQuit();
                    return;
                }
                playTurn(gameId, positions);
            } catch (IllegalArgumentException | DataAccessException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private List<String> readPosition(Team currentTeam) {
        while (true) {
            try {
                return inputView.readPosition(currentTeam);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void printFinishedGame(JanggiGame game) {
        printBoardAndScore(game);
        outputView.printWinner(game.getWinner());
        inputView.waitForEnter();
    }

    private void playTurn(long gameId, List<String> positions) {
        service.playTurn(gameId, new Movement(
                Position.from(positions.get(FROM_INDEX)),
                Position.from(positions.get(TO_INDEX))));
    }

    private void printBoardAndScore(JanggiGame game) {
        outputView.printBoard(game.getBoard(), game.getScore());
    }

    private boolean isQuitCommand(List<String> positions) {
        return positions.getFirst().equalsIgnoreCase(QUIT_COMMAND);
    }
}
