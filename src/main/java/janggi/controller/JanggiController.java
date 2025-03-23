package janggi.controller;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.Board;
import janggi.domain.BoardSetup;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Set;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final BoardSetup redBoardSetup = inputView.inputBoardSetup(RED);
        final BoardSetup blueBoardSetup = inputView.inputBoardSetup(BLUE);
        final Board board = new Board(redBoardSetup, blueBoardSetup);
        final List<Piece> pieces = board.getPieces();
        outputView.printBoard(pieces);

        while (true) {
            final Team currentTurn = board.getTurn();
            outputView.printTurn(currentTurn);

            final Position position = inputView.inputPiecePosition();
            final Piece selectPiece = board.selectPiece(position);

            final Set<Route> possibleRoutes = board.findPossibleRoutes(selectPiece);
            outputView.printPossibleRoutes(possibleRoutes);

            final Position destination = inputView.inputDestination();

            board.movePiece(destination, selectPiece);
            outputView.printBoard(pieces);
            board.changeTurn();
        }
    }
}
