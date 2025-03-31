package janggi;

import janggi.dao.piece.PieceDaoImpl;
import janggi.dao.piece.PieceHistoryManager;
import janggi.dao.turn.TurnDaoImpl;
import janggi.dao.turn.TurnManager;
import janggi.view.InputView;
import janggi.view.ResultView;

public class JanggiApplication {
    public static void main(final String[] args) {
        final InputView inputView = new InputView();
        final ResultView resultView = new ResultView();
        final PieceHistoryManager pieceHistoryManager = new PieceHistoryManager(PieceDaoImpl.getPieceDao());
        final TurnManager turnManager = new TurnManager(TurnDaoImpl.getTurnDao());
        final JanggiConsole janggiConsole = new JanggiConsole(inputView, resultView, pieceHistoryManager, turnManager);
        janggiConsole.start();
    }
}
