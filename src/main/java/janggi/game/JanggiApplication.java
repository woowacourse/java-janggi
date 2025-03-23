package janggi.game;

import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;

public class JanggiApplication {

    private final InputView inputView;
    private final BoardView boardView;

    private JanggiApplication() {
        inputView = new InputView();
        boardView = new BoardView();
    }

    public static void main(String[] args) {
        JanggiApplication janggiApplication = new JanggiApplication();
        janggiApplication.run();
    }

    private void run() {
        Board board = Board.init(Team.CHO);

        do {
            boardView.displayBoard(board);
            boardView.printTeam(board.getTurn());

            handleMoveException(() -> {
                Point startPoint = inputView.readStartPoint();
                boardView.printSelectedPiece(board.findPieceByPoint(startPoint));

                Point targetPoint = inputView.readTargetPoint();
                board.move(startPoint, targetPoint);

                boardView.printMovingResult(startPoint, targetPoint);
                board.reverseTurn();
            });
        } while (inputView.readGameStart());
    }

    private void handleMoveException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
