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
        JanggiGame janggiGame = createInitialGame();

        resultView.printBoard(janggiGame.getPieces());

        while (janggiGame.canContinueGame()) {
            final Team currentTeam = janggiGame.getCurrentTeam();
            resultView.printOrder(currentTeam);
            ExceptionHandler.retry(() -> janggiGame.move(inputView.readMovingPosition()));
            resultView.printBoard(janggiGame.getPieces());
        }

        resultView.printJanggiResult(janggiGame.getWinningTeam());
    }

    private JanggiGame createInitialGame() {
        final BoardFactory boardFactory = new BoardFactory();

        resultView.printSetting();

        SangSetting choSangSetting = ExceptionHandler.repeat(() -> SangSetting.selectSetting((
                inputView.readElephantSetting(Team.CHO))));
        SangSetting hanSangSetting = ExceptionHandler.repeat(() -> SangSetting.selectSetting((
                inputView.readElephantSetting(Team.HAN))));
        final Turn turn = new Turn();
        final Board board = boardFactory.makeBoard(choSangSetting, hanSangSetting);

        return new JanggiGame(board, turn);
    }
}
