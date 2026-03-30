package janggi.controller;

import janggi.domain.board.point.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
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

            PieceDto pieceDto = getResult(game);
            outputView.printBoardWithPath(board, pieceDto.destinations());

            retry(this::movePath, game, pieceDto.from());
        }
    }

    private PieceDto getResult(Game game) {
        Point from = retry(inputView::readPoint);
        Set<Point> destinations = game.destinations(from);
        return new PieceDto(from, destinations);
    }

    private void movePath(Game game, Point from) {
        Point to = retry(() -> inputView.readDestination().orElse(null));
        if (to == null) {
            return;
        }
        game.move(from, to);
    }

    private <T, U> void retry(BiConsumer<T, U> consumer, T t, U u) {
        while (true) {
            try {
                consumer.accept(t, u);
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

    private record PieceDto(Point from, Set<Point> destinations) {
    }
}
