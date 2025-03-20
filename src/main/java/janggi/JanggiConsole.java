package janggi;

import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.piece.Team;
import janggi.view.InputView;
import janggi.view.ResultView;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        final Queue<Team> orders = new LinkedList<>(List.of(Team.CHO, Team.HAN));

        while (board.canContinue()) {
            final Team currentTeam = orders.poll();
            board.move(inputView.readMovingPosition(currentTeam));
            resultView.printBoard(board.getPieces());
            orders.offer(currentTeam);
        }

        resultView.printJanggiResult(board.findWinningTeam());
    }
}
