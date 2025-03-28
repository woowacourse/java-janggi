package janggi.game;

import janggi.piece.Piece;
import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.ResultView;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class JanggiApplication {

    private final InputView inputView;
    private final BoardView boardView;
    private final ResultView resultView;

    private JanggiApplication() {
        inputView = new InputView();
        boardView = new BoardView();
        resultView = new ResultView();
    }

    public static void main(String[] args) {
        JanggiApplication janggiApplication = new JanggiApplication();
        janggiApplication.run();
    }

    private void run() {
        if (inputView.readGameStart()) {
            Game game = new Game();

            while (game.canContinue()) {
                boardView.displayBoard(game.getBoard());
                boardView.printTeam(game.getTurn());

                Piece movingPiece = choosePieceUntilSuccess(() -> {
                    Point startPoint = inputView.readStartPoint();
                    return game.findMovingPiece(startPoint);
                });

                movePieceUntilSuccess(piece -> {
                    Point targetPoint = inputView.readTargetPoint();
                    game.move(piece, targetPoint);
                }
                , movingPiece);

                game.reverseTurn();
            }

            resultView.printResult(game);
        }
    }

    private <T> T choosePieceUntilSuccess(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private <T> void movePieceUntilSuccess(Consumer<T> action, T input) {
        while (true) {
            try {
                action.accept(input);
                return;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
