package janggi.controller;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.game.GameSession;
import janggi.domain.side.Side;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

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

        if (activeGameId.isPresent() && inputView.readContinueGame()) {
            play(gameService.continueGame(activeGameId.get()));
            return;
        }

        activeGameId.ifPresent(gameService::finish);

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
            Optional<Side> winner = retryMove(session, from);

            if (winner.isEmpty()) {
                continue;
            }

            outputView.printScore(Side.CHO, game.getScore(Side.CHO));
            outputView.printScore(Side.HAN, game.getScore(Side.HAN));

            if (!winner.get().equals(Side.NONE)) {
                outputView.printBoard(game.getBoard());
                outputView.printGameResult(winner.get());
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

    private Optional<Side> retryMove(GameSession session, Point from) {
        Game game = session.game();

        while (true) {
            Optional<Point> to = retry(() -> inputView.readDestination());

            if (to.isEmpty()) {
                return Optional.empty();
            }

            try {
                return Optional.ofNullable(gameService.move(session, from, to.get()));
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                outputView.printBoardWithPath(game.getBoard(), game.destinations(from));
            }
        }
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
