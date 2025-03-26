package janggi;

import janggi.piece.PiecesFactory;
import janggi.board.BoardOrder;
import janggi.piece.Pieces;
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
        final Pieces pieces = makePieces();
        Turn turn = Turn.initialize();

        while (pieces.canContinue()) {
            final Team currentTeam = turn.getTeam();
            resultView.printOrder(currentTeam);
            ExceptionHandler.retry(() -> pieces.move(inputView.readMovingPosition(), currentTeam));
            resultView.printBoard(pieces);
            turn = turn.moveNextTurn();
        }

        resultView.printJanggiResult(pieces.findWinningTeam());
    }

    private Pieces makePieces() {
        final PiecesFactory piecesFactory = new PiecesFactory();
        final int choOrder = StringParser.parseInt(inputView.readChoBoardOrder());
        final int hanOrder = StringParser.parseInt(inputView.readHanBoardOrder());
        final Pieces pieces = piecesFactory.makePiecesByOrder(BoardOrder.from(choOrder), BoardOrder.from(hanOrder));
        resultView.printBoard(pieces);
        return pieces;
    }
}
