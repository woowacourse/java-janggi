package janggi.controller;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.state.GameContext;
import janggi.exception.JanggiException;
import janggi.domain.strategy.ArrangementStrategy;
import janggi.domain.strategy.ArrangementStrategyFactory;
import janggi.domain.strategy.BoardAssembler;
import janggi.domain.strategy.IntersectionInitializer;
import janggi.domain.strategy.PalaceIntersectionInitializer;
import janggi.domain.strategy.StrategyLabel;
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

        GameContext gameContext = GameContext.createInProgress(board.getAlivePieces(), Side.HAN);
        while (gameContext.isInProgress()) {
            printBoard(board);

            Side currentSide = gameContext.getCurrentSide();
            view.respondCurrentSide(SideViewResolver.toDisplayName(currentSide));

            retryAction(() -> {
                Location from = askLocationOfPiece(currentSide, board);
                Location to = askLocationToMove(currentSide, board);
                Piece removedPiece = board.move(from, to);
                gameContext.update(removedPiece);
            });
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
