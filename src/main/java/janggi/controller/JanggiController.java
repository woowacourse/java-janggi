package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.PieceSetup;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.exception.DataAccessException;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;

public class JanggiController {
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

                if (command == Command.LOAD) {
                    loadGame();
                    continue;
                }

                if (command == Command.NEW) {
                    long gameId = createGame();
                    playGame(gameId);
                    continue;
                }

                if (command == Command.DELETE) {
                    deleteGame();
                    continue;
                }
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void loadGame() {
        while (true) {
            try {
                long gameId = inputView.readLoadGameId();
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
        JanggiGame game = service.loadGame(gameId);
        printBoardAndScore(game);
        while (!game.isFinished()) {
            try {
                List<String> positions = inputView.readPosition(game.getCurrentTeam());
                if (isQuitCommand(positions)) {
                    outputView.printGameQuit();
                    return;
                }
                game = playTurn(gameId, positions);
                printBoardAndScore(game);
            } catch (IllegalArgumentException | DataAccessException e) {
                outputView.printError(e.getMessage());
            }
        }
        outputView.printWinner(game.getWinner());
        inputView.waitForEnter();
    }

    private JanggiGame playTurn(long gameId, List<String> positions) {
        return service.playTurn(gameId, new Movement(
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
