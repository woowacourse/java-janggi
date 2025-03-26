package janggi;

import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.board.BoardOrder;
import janggi.piece.Team;
import janggi.turn.Turn;
import janggi.utils.ExceptionHandler;
import janggi.utils.StringParser;
import janggi.view.InputView;
import janggi.view.ResultView;

public class JanggiConsole {

    private final InputView inputView;
    private final ResultView resultView;

    public JanggiConsole(final InputView inputView, final ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void start() {
        final Board board = makeBoard();
        Turn turn = Turn.initialize();

        while (board.canContinue()) {
            final Team currentTeam = turn.getTeam();
            resultView.printOrder(currentTeam);
            ExceptionHandler.retry(() -> board.move(inputView.readMovingPosition(), currentTeam));
            resultView.printBoard(board.getPieces());
            turn = turn.moveNextTurn();
        }

        resultView.printJanggiResult(board.findWinningTeam());
    }

    private Board makeBoard() {
        final BoardFactory boardFactory = new BoardFactory();
        final int choOrder = StringParser.parseInt(inputView.readChoBoardOrder());
        final int hanOrder = StringParser.parseInt(inputView.readHanBoardOrder());
        final Board board = boardFactory.makeBoard(BoardOrder.from(choOrder), BoardOrder.from(hanOrder));
        resultView.printBoard(board.getPieces());
        return board;
    }
}
