package janggi.controller;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.position.Route;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.UserContinueResponse;
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
        Board board = new Board();
        List<Piece> pieces = board.getPieces();
        outputView.printBoard(pieces);

        while (true) {
            Team currentTurn = board.getTurn();
            outputView.printTurn(currentTurn);
            UserContinueResponse userContinueResponse = UserExceptionHandler.retryUntilSuccess(inputView::continueGame);

            if (userContinueResponse == UserContinueResponse.QUIT) {
                break;
            }
            Piece selectedPiece = UserExceptionHandler.retryUntilSuccess(() -> selectPiece(board));
            Set<Route> possibleRoutes = board.findPossibleRoutes(selectedPiece);

            if (possibleRoutes.isEmpty()) {
                outputView.printCannotMove();
            }
            if (!possibleRoutes.isEmpty()) {
                outputView.printPossibleRoutes(possibleRoutes);
                UserExceptionHandler.retryUntilSuccess(() -> movePiece(board, selectedPiece, possibleRoutes));
            }

            outputView.printBoard(pieces);
            board.changeTurn();
        }
    }

    private Piece selectPiece(Board board) {
        Position position = inputView.inputPiecePosition();
        return board.selectPiece(position);
    }

    private void movePiece(Board board, Piece selectedPiece, Set<Route> possibleRoutes) {
        Position destination = inputView.inputDestination();
        board.movePiece(destination, selectedPiece, possibleRoutes);
    }
}
