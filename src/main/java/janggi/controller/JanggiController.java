package janggi.controller;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.piece.unit.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;
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
        while (game.canPlay()) {
            Map<Point, Piece> board = game.getBoard();
            outputView.printBoard(board);
            outputView.printSide(game.getTurn());

            Point from = retry(() -> getPoint(game));
            outputView.printBoardWithPath(board, game.destinations(from));
            retry(() -> movePath(game, from));
        }
        outputView.printWinner(game.winnerSide());
    }

    private Point getPoint(Game game) {
        Point from = retry(inputView::readPoint);
        if (!game.canMove(from)) {
            throw new IllegalArgumentException("움직일 수 없습니다.");
        }
        return from;
    }

    private void movePath(Game game, Point from) {
        Point to = inputView.readDestination();
        if (to == null) {
            return;
        }
        game.move(from, to);
    }

    private void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
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
