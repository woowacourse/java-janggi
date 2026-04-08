package janggi.controller;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.state.GameContext;
import janggi.domain.strategy.ArrangementStrategy;
import janggi.domain.strategy.ArrangementStrategyFactory;
import janggi.domain.strategy.IntersectionInitializer;
import janggi.domain.strategy.PalaceIntersectionInitializer;
import janggi.domain.strategy.StrategyLabel;
import janggi.exception.JanggiException;
import janggi.sevice.JanggiService;
import janggi.sevice.dto.GameInformation;
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
    private final JanggiService janggiService;

    public JanggiFlow(ApplicationView view, JanggiService janggiService) {
        this.view = view;
        this.janggiService = janggiService;
        this.arrangementFactory = new ArrangementStrategyFactory();
        this.intersectionInitializer = new PalaceIntersectionInitializer();
    }

    public void process() {
        //보드 초기화
        GameInformation gameInformation = initializeGameInformation();
        Board board = gameInformation.board();
        Side currentSide = gameInformation.currentSide();

        //컨텍스트 생성
        GameContext gameContext = GameContext.createInProgress(board.getAlivePieces(), currentSide);
        while (gameContext.isInProgress()) {
            //출력
            printBoard(board);
            currentSide = gameContext.getCurrentSide();
            view.respondCurrentSide(SideViewResolver.toDisplayName(currentSide));

            System.out.println("alive: " + board.getAlivePieces().size());
            //턴 실행
            Side finalCurrentSide = currentSide;
            retryAction(() -> {
                Location from = askLocationOfPiece(finalCurrentSide, board);
                Location to = askLocationToMove(finalCurrentSide, board);
                janggiService.movePiece(gameInformation, from, to, gameContext);
            });
        }
        janggiService.endGame(gameInformation.gameId());
    }

    private GameInformation initializeGameInformation() {
        List<Long> activeGameIds = janggiService.findActiveGameIds();
        if (activeGameIds.isEmpty()) {
            List<ArrangementStrategy> strategies = Stream.of(Side.values())
                    .map(this::askStrategy)
                    .toList();
            System.out.println("create new");
            return janggiService.createGame(strategies, intersectionInitializer);
        }
        Long gameId = activeGameIds.getFirst();
        System.out.println("load: " + gameId);
        return janggiService.loadGameInformation(gameId, intersectionInitializer);
    }

    private void printBoard(Board board) {
        List<List<String>> displayBoard = board.to2DArray().stream()
                .map(row -> row.stream()
                        .map(PieceViewResolver::toDisplayName)
                        .toList())
                .toList();
        view.respondBoardArray(displayBoard);
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
