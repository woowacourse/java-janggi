package janggi.controller;

import janggi.domain.board.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.state.GameContext;
import janggi.domain.strategy.arrangement.ArrangementStrategy;
import janggi.domain.strategy.arrangement.ArrangementStrategyFactory;
import janggi.domain.strategy.intersection.IntersectionInitializer;
import janggi.domain.strategy.intersection.PalaceIntersectionInitializer;
import janggi.domain.strategy.StrategyLabel;
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

public class JanggiFlow {

    private final ApplicationView view;
    private final IntersectionInitializer intersectionInitializer;
    private final JanggiService janggiService;

    public JanggiFlow(ApplicationView view, JanggiService janggiService) {
        this.view = view;
        this.janggiService = janggiService;
        this.intersectionInitializer = new PalaceIntersectionInitializer();
    }

    public void process() {
        GameInformation gameInformation = initializeGameInformation();
        Board board = gameInformation.board();

        GameContext gameContext = GameContext.createInProgress(board.getAlivePieces(), gameInformation.currentSide());
        while (gameContext.isInProgress()) {
            printBoard(board);
            view.respondCurrentSide(SideViewResolver.toDisplayName(gameContext.getCurrentSide()));
            retryAction(() -> playTurn(gameInformation, gameContext));
        }
        finish(gameContext, gameInformation);
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

    private void playTurn(GameInformation gameInformation, GameContext gameContext) {
        Location from = askLocationOfPiece(gameContext.getCurrentSide(), gameInformation.board());
        Location to = askLocationToMove(gameContext.getCurrentSide(), gameInformation.board());
        janggiService.movePiece(gameInformation, from, to, gameContext);
    }

    private void finish(GameContext gameContext, GameInformation gameInformation) {
        view.respondWinner(gameContext.getWinner());
        janggiService.endGame(gameInformation.gameId());
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
