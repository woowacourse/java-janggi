package janggi.controller;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.exception.JanggiException;
import janggi.strategy.ArrangementStrategy;
import janggi.strategy.ArrangementStrategyFactory;
import janggi.strategy.BoardAssembler;
import janggi.strategy.IntersectionInitializer;
import janggi.strategy.PalaceIntersectionInitializer;
import janggi.strategy.StrategyLabel;
import janggi.view.ApplicationView;
import janggi.view.resolver.PieceViewResolver;
import janggi.view.resolver.SideViewResolver;
import janggi.view.resolver.StrategyViewResolver;
import java.util.List;
import java.util.stream.Stream;

public class JanggiFlow {

    private final ApplicationView view;
    private final ArrangementStrategyFactory arrangementFactory;
    private final IntersectionInitializer intersectionInitializer;

    public JanggiFlow(ApplicationView view) {
        this.view = view;
        this.arrangementFactory = new ArrangementStrategyFactory();
        this.intersectionInitializer = new PalaceIntersectionInitializer();
    }

    public void process() {
        Board board = initializeBoard();

        Side current = Side.HAN;
        while (board.isNotEmpty()) {
            printBoard(board);
            view.respondCurrentSide(SideViewResolver.toDisplayName(current));

            final Side turnSide = current;
            retryAction(() -> {
                Location from = askLocationOfPiece(turnSide, board);
                Location to = askLocationToMove(turnSide, board);
                board.move(from, to);
            });
            current = current.switchTurn();
        }
    }

    private void printBoard(Board board) {
        List<List<String>> displayBoard = board.to2DArray().stream()
                .map(row -> row.stream()
                        .map(PieceViewResolver::toDisplayName)
                        .toList())
                .toList();
        view.respondBoardArray(displayBoard);
    }

    private Board initializeBoard() {
        List<ArrangementStrategy> strategies = Stream.of(Side.values()).map(this::askStrategy).toList();
        return Board.create(BoardAssembler.of(strategies, intersectionInitializer));
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
        return arrangementFactory.createStrategy(StrategyLabel.from(decisionNumber), side);
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
