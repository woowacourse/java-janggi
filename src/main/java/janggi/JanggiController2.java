package janggi;

import janggi.domain.board.PieceSetup;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;

public class JanggiController2 {
    private static final String QUIT_COMMAND = "end";
    private static final String CREATE_GAME_COMMAND = "new";
    private static final int FROM_INDEX = 0;
    private static final int TO_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService service;

    public JanggiController2(InputView inputView, OutputView outputView, JanggiService service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
    }

    public void run() {
        while (true) {
            outputView.printGameList(service.findAllGames());

            String command = inputView.readCommand();
            if (isQuitCommand(command)) {
                outputView.printGameEnd();
                return;
            }
            if (isCreateCommand(command)) {
                long gameId = createGame();
                playGame(gameId);
                continue;
            }
            long gameId = Long.parseLong(command);
            playGame(gameId);
        }
    }

    private boolean processCommand() {
        return false;
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

    private void playGame(long gameId) {
        JanggiGame2 game = service.loadGame(gameId);
        outputView.printBoard(game.getBoard(), game.getScore());

        while (!game.isFinished()) {
            try {
                List<String> positions = inputView.readPosition(game.getCurrentTeam());
                if (isQuitCommand(positions.getFirst())) {
                    outputView.printGameEnd();
                    return;
                }
                Position from = Position.from(positions.get(FROM_INDEX));
                Position to = Position.from(positions.get(TO_INDEX));
                game = service.playTurn(gameId, new Movement(from, to));
                outputView.printBoard(game.getBoard(), game.getScore());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
        outputView.printWinner(game.getWinner());
    }

    private boolean isQuitCommand(String command) {
        return command.equals(QUIT_COMMAND);

    }

    private boolean isCreateCommand(String command) {
        return command.equals(CREATE_GAME_COMMAND);
    }
}
