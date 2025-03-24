package janggi;

import janggi.Team.Team;
import janggi.Team.Turn;
import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.board.SangSetting;
import janggi.utils.ExceptionHandler;
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
        final BoardFactory boardFactory = new BoardFactory();
        final Turn turn = new Turn();

        resultView.printSetting();
        SangSetting choSangSetting = ExceptionHandler.repeat(() -> SangSetting.selectSetting((
                inputView.readElephantSetting(turn.getAndTurnOver()))));
        SangSetting hanSangSetting = ExceptionHandler.repeat(() -> SangSetting.selectSetting((
                inputView.readElephantSetting(turn.getAndTurnOver()))));
        final Board board = boardFactory.makeBoard(choSangSetting, hanSangSetting);

        resultView.printBoard(board.getPieces());

        while (board.canContinue()) {
            final Team currentTeam = turn.getCurrentTeam();
            resultView.printOrder(currentTeam);
            ExceptionHandler.retry(() -> board.move(inputView.readMovingPosition(), currentTeam));
            resultView.printBoard(board.getPieces());
            turn.turnOver();
        }

        resultView.printJanggiResult(board.findWinningTeam());
    }
}
