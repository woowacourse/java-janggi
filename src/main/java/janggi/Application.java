package janggi;

import janggi.controller.JanggiController;
import janggi.domain.board.BoardSetup;
import janggi.domain.board.InitialBoard;
import janggi.domain.board.PlayingBoard;
import janggi.domain.piece.PieceColor;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.function.Supplier;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        InitialBoard initialBoard = getWithRetry(() -> setupBoard(inputView));
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());
        outputView.printBoard(playingBoard);

        JanggiController controller = new JanggiController(inputView, outputView, playingBoard);
        controller.run();
    }

    private static InitialBoard setupBoard(InputView inputView) {
        BoardSetup redSetup = getBoardSetup(inputView, PieceColor.RED);
        BoardSetup blueSetup = getBoardSetup(inputView, PieceColor.BLUE);
        return InitialBoard.createBoard(redSetup, blueSetup);
    }

    private static BoardSetup getBoardSetup(InputView inputView, PieceColor teamColor) {
        int setNumber = inputView.readBoardSetup(teamColor);
        return BoardSetup.from(setNumber);
    }

    public static <T> T getWithRetry(Supplier<T> task) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
