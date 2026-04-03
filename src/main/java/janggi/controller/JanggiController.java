package janggi.controller;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        BoardSetUp choBoardSetUp = retry(() -> inputView.readBoardSetup(Side.CHO));
        BoardSetUp hanBoardSetUp = retry(() -> inputView.readBoardSetup(Side.HAN));

        Game game = Game.createGame(choBoardSetUp, hanBoardSetUp);
        play(game);
    }

    private void play(Game game) {
        while (true) {
            Map<Point, Piece> board = game.getBoard();
            outputView.printBoard(board);
            outputView.printSide(game.getTurn());

            Side winSide = selectMove(game, board);

            if (winSide != Side.NONE) {
                outputView.printBoard(game.getBoard());
                outputView.printGameResult(winSide);
                break;
            }
        }
    }

    private Side selectMove(Game game, Map<Point, Piece> board) {
        while (true) {
            Point from = retry(() -> printPath(inputView.readPoint(), game, board));
            Optional<Point> to = retry(() -> inputView.readDestination());

            if (to.isEmpty()) {
                continue;
            }

            return retry(() -> move(game, from, to.get()));
        }
    }

    private Side move(Game game, Point from, Point to) {
        return game.move(from, to);
    }

    private Point printPath(Point from, Game game, Map<Point, Piece> board) {
        Set<Point> destinations = game.destinations(from);
        outputView.printBoardWithPath(board, destinations);
        return from;
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
