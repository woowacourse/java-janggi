package janggi.controller;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.PieceCancelException;
import java.util.Map;
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
        retry(() -> play(game));
    }

    private void play(Game game) {
        while (true) {
            Map<Point, Piece> board = game.getBoard();
            outputView.printBoard(board);
            outputView.printSide(game.getTurn());

            Point from = retry(() -> printPath(inputView.readPoint(), game, board));
            retry(() -> move(game, from, inputView.readDestination()));
        }
    }

    private void move(Game game, Point from, Point to) {
        game.move(from, to);
    }


    private Point printPath(Point from, Game game, Map<Point, Piece> board) {
        Set<Point> destinations = game.destinations(from);
        outputView.printBoardWithPath(board, destinations);
        return from;
    }

    private void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                break;
            } catch (PieceCancelException e) {
                break;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
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
