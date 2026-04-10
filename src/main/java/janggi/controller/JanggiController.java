package janggi.controller;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.Location;
import janggi.domain.strategy.StrategyLabel;
import janggi.domain.strategy.arrangement.ArrangementStrategy;
import janggi.domain.strategy.arrangement.ArrangementStrategyFactory;
import janggi.domain.strategy.intersection.IntersectionInitializer;
import janggi.domain.strategy.intersection.PalaceIntersectionInitializer;
import janggi.exception.JanggiException;
import janggi.service.JanggiService;
import janggi.service.dto.GameInformation;
import janggi.view.ApplicationView;
import janggi.view.Decision;
import janggi.view.resolver.PieceViewResolver;
import janggi.view.resolver.SideViewResolver;
import janggi.view.resolver.StrategyViewResolver;
import java.util.List;
import java.util.stream.Stream;

public class JanggiController {

    private final ApplicationView view;
    private final IntersectionInitializer intersectionInitializer;
    private final JanggiService janggiService;

    public JanggiController(ApplicationView view, JanggiService janggiService) {
        this.view = view;
        this.janggiService = janggiService;
        this.intersectionInitializer = new PalaceIntersectionInitializer();
    }

    public void process() {
        GameInformation gameInformation = initializeGameInformation();

        while (gameInformation.isInProgress()) {
            printBoard(gameInformation.getBoard());
            view.respondCurrentSide(SideViewResolver.toDisplayName(gameInformation.getCurrentSide()));
            retryAction(() -> playTurn(gameInformation));
        }
        finish(gameInformation);
    }

    private GameInformation initializeGameInformation() {
        List<Long> activeGameIds = janggiService.findActiveGameIds();
        if (hasSavedGames(activeGameIds) && askContinueGame()) {
            return janggiService.loadGameInformation(activeGameIds.getFirst(), intersectionInitializer);
        }
        return createNewGame();
    }

    private boolean hasSavedGames(List<Long> activeGameIds) {
        return !activeGameIds.isEmpty();
    }

    private GameInformation createNewGame() {
        List<ArrangementStrategy> strategies = Stream.of(Side.values())
                .map(this::askStrategy)
                .toList();
        return janggiService.createGame(strategies, intersectionInitializer);
    }

    private void playTurn(GameInformation gameInformation) {
        Location from = askLocationOfPiece(gameInformation.getCurrentSide(), gameInformation.getBoard());
        Location to = askLocationToMove(gameInformation.getCurrentSide(), gameInformation.getBoard());
        janggiService.movePiece(gameInformation, from, to);
    }

    private void finish(GameInformation gameInformation) {
        view.respondWinner(gameInformation.getWinner());
        janggiService.endGame(gameInformation.getGameId());
    }

    private void printBoard(Board board) {
        List<List<String>> displayBoard = board.to2DArray().stream()
                .map(PieceViewResolver::toDisplayName)
                .toList();
        view.respondBoardArray(displayBoard);
    }

    private boolean askContinueGame() {
        Decision decision = view.requestGameContinueDecision();
        return decision.isTrue();
    }

    private Location askLocationOfPiece(Side current, Board board) {
        List<Integer> locationOfPiece = view.requestLocationOfPiece();
        Location verifiedLocation = Location.from(locationOfPiece);
        board.validateLocationOfPiece(current, verifiedLocation);
        return verifiedLocation;
    }

    private Location askLocationToMove(Side current, Board board) {
        List<Integer> locationToMove = view.requestLocationToMove();
        Location verifiedLocation = Location.from(locationToMove);
        board.validateLocationToMove(current, verifiedLocation);
        return verifiedLocation;
    }

    private ArrangementStrategy askStrategy(Side side) {
        List<String> strategyOptions = Stream.of(StrategyLabel.values())
                .map(StrategyViewResolver::toDisplayName)
                .toList();
        int decisionNumber = view.requestArrangementStrategyDecision(SideViewResolver.toDisplayName(side),
                strategyOptions);
        return ArrangementStrategyFactory.createStrategy(StrategyLabel.from(decisionNumber), side);
    }

    private void retryAction(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (JanggiException | IllegalArgumentException e) {
                view.respondErrorMessage(e);
            }
        }
    }
}
