package janggi;

import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.piece.Team;
import janggi.utils.ExceptionHandler;
import janggi.view.InputView;
import janggi.view.ResultView;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class JanggiConsole {

    private final InputView inputView;
    private final ResultView resultView;

    public JanggiConsole(final InputView inputView, final ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void start() {
        final BoardFactory boardFactory = new BoardFactory();
        final Board board = boardFactory.makeBoard();
        resultView.printBoard(board.getPieces());
        final Deque<Team> orders = new ArrayDeque<>(List.of(Team.CHO, Team.HAN));

        while (board.canContinue()) {
            final Team currentTeam = orders.poll();
            resultView.printOrder(currentTeam);
            ExceptionHandler.retry(() -> board.move(inputView.readMovingPosition(), currentTeam));
            resultView.printBoard(board.getPieces());
            orders.offer(currentTeam);
        }

        resultView.printJanggiResult(board.findWinningTeam());
    }
}
