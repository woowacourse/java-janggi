package janggi.controller;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.game.GameResult;
import janggi.domain.game.GameSession;
import janggi.domain.side.Side;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;

    public JanggiController(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        Optional<Integer> activeGameId = gameService.findActiveGameId();

        if (activeGameId.isPresent() && retry(() -> inputView.readContinueGame())) {
            play(gameService.continueGame(activeGameId.get()));
            return;
        }
        activeGameId.ifPresent(id -> gameService.finish(id, Side.NONE));

        BoardSetUp choBoardSetUp = retry(() -> inputView.readBoardSetup(Side.CHO));
        BoardSetUp hanBoardSetUp = retry(() -> inputView.readBoardSetup(Side.HAN));
        play(gameService.createGame(choBoardSetUp, hanBoardSetUp));
    }

    private void play(GameSession session) {
        Game game = session.game();

        while (true) {
            outputView.printBoard(game.getBoard());
            outputView.printSide(game.getTurn());

            Point from = retry(() -> selectFrom(game));
            Optional<GameResult> result = retryMove(session, from);

            if (result.isEmpty()) {
                continue;
            }

            if (processResult(session, result.get())) {
                break;
            }

        }
    }

    private Point selectFrom(Game game) {
        Point from = inputView.readPoint();
        Set<Point> destinations = game.destinations(from);
        outputView.printBoardWithPath(game.getBoard(), destinations);
        return from;
    }

    private Optional<GameResult> retryMove(GameSession session, Point from) {
        while (true) {
            Optional<Point> to = retry(() -> inputView.readDestination());

            if (to.isEmpty()) {
                return Optional.empty();
            }

            Optional<GameResult> result = tryMove(session, from, to.get());

            if (result.isPresent()) {
                return result;
            }
        }
    }

    private Optional<GameResult> tryMove(GameSession session, Point from, Point to) {
        try {
            return Optional.of(gameService.move(session, from, to));
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            outputView.printBoardWithPath(session.game().getBoard(), session.game().destinations(from));
            return Optional.empty();
        }
    }

    private boolean processResult(GameSession session, GameResult result) {
        Game game = session.game();
        outputView.printScore(Side.CHO, game.getScore(Side.CHO));
        outputView.printScore(Side.HAN, game.getScore(Side.HAN));

        if (result.isGameOver()) {
            gameService.finish(session.gameId(), result.getWinner());
            outputView.printBoard(game.getBoard());
            outputView.printGameResult(result.getWinner());
            return true;
        }
        return false;
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
