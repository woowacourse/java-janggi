package janggi.controller;

import janggi.domain.Board;
import janggi.domain.GameInitializeMode;
import janggi.domain.Team;
import janggi.domain.dto.GameSession;
import janggi.domain.Position;
import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.service.JanggiService;
import janggi.util.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    private final JanggiService janggiGameService;

    public JanggiController(JanggiService janggiGameService) {
        this.janggiGameService = janggiGameService;
    }

    public void run() {
        GameSession session = initializeGame();

        Board board = session.board();
        long gameId = session.gameId();
        Team turn = session.turn();

        while (!board.gameEnd()) {
            try {
                Position fromPosition = readFromPosition();
                Position toPosition = readToPosition();

                janggiGameService.move(gameId, board, fromPosition, toPosition);
                turn = turn.next();

                OutputView.printBoard(board);
                OutputView.printTeamScore(board);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private GameSession initializeGame() {
        while (true) {
            try {
                String modeInput = InputView.askGameMode();
                GameInitializeMode mode = GameInitializeMode.from(modeInput);

                return getGameSession(mode);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private GameSession getGameSession(GameInitializeMode mode) {
        if (mode == GameInitializeMode.NEW) {
            return initializeNewGame();
        }
        return initializePastGame();
    }

    private GameSession initializeNewGame() {
        Board board = new Board(new BasicPlacementStrategy());
        Team initialTurn = Team.initialTeam();
        long gameId = janggiGameService.createGame(board, initialTurn);
        OutputView.printGameId(gameId);
        OutputView.printBoard(board);
        return new GameSession(gameId, board, initialTurn);
    }

    private GameSession initializePastGame() {
        long gameId = Long.parseLong(InputView.askGameId());
        Board board = janggiGameService.loadGame(gameId);
        Team turn = janggiGameService.loadTurn(gameId);

        OutputView.printBoard(board);
        return new GameSession(gameId, board, turn);
    }

    private Position readFromPosition() {
        while (true) {
            try {
                String rawInput = InputView.askFromPosition();
                List<String> positionValues = InputParser.splitByDelimiter(rawInput);
                return Position.from(positionValues);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private Position readToPosition() {
        while (true) {
            try {
                String rawInput = InputView.askToPosition();
                List<String> positionValues = InputParser.splitByDelimiter(rawInput);
                return Position.from(positionValues);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
