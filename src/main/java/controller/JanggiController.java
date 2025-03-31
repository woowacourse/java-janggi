package controller;

import dao.PieceDao;
import domain.Board;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.position.InitialChessPiecePositionsGenerator;
import domain.type.ChessTeam;
import game.Janggi;
import game.Turn;
import util.LoopTemplate;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final PieceDao pieceDao;

    public JanggiController(final InputView inputView, final OutputView outputView, final PieceDao pieceDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.pieceDao = pieceDao;
    }

    public void run() {
        final InitialChessPiecePositionsGenerator initialChessPiecePositionsGenerator = new InitialChessPiecePositionsGenerator();
        final ChessPiecePositions chessPiecePositions = ChessPiecePositions.from(initialChessPiecePositionsGenerator);
        final Board board = new Board(chessPiecePositions);
        final Turn turn = new Turn(ChessTeam.BLUE);
        final Janggi janggi = new Janggi(1L, board, turn);
        processGame(janggi);
    }

    private void processGame(final Janggi janggi) {
        outputView.printBoard(janggi.getChessPiecesMapView());
        LoopTemplate.tryCatchLoop(() -> {
            outputView.printCurrentTeam(janggi.getCurrentTeam());
            final ChessPosition fromPosition = inputView.readFromPosition();
            janggi.validateFromPosition(fromPosition);
            outputView.printAvailableDestinations(janggi.getAvailableDestinations(fromPosition));
            final ChessPosition toPosition = inputView.readToPosition();
            janggi.processTurn(fromPosition, toPosition);
        });
        processGame(janggi);
    }
}
