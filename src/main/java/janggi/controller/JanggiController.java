package janggi.controller;

import janggi.domain.Board;
import janggi.domain.GameInitializeMode;
import janggi.domain.Team;
import janggi.domain.GameSession;
import janggi.domain.Position;
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
                Position fromPosition = readFromPosition(gameId, board);
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
        GameSession gameSession = janggiGameService.createNewGame();
        OutputView.printGameId(gameSession.gameId());
        OutputView.printBoard(gameSession.board());
        return gameSession;
    }

    private GameSession initializePastGame() {
        List<Long> gameIds = janggiGameService.loadPastGameIds();
        OutputView.printGameIds(gameIds);

        long gameId = Long.parseLong(InputView.askGameId());
        GameSession gameSession = janggiGameService.loadPastGame(gameId);

        OutputView.printBoard(gameSession.board());
        return gameSession;
    }

    private Position readFromPosition(Long gameId, Board board) {
        while (true) {
            try {
                String rawInput = InputView.askFromPosition();
                List<String> positionValues = InputParser.splitByDelimiter(rawInput);
                Position position = Position.from(positionValues);
                janggiGameService.validateFrom(gameId, board, position);
                return position;
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
