package janggi.controller;

import janggi.domain.Board;
import janggi.domain.InitialElephantSetting;
import janggi.domain.PiecesInitializer;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
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
        Board board = new Board(PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT));
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
            Set<Position> possibleDestinations = board.findDestinations(selectedPiece);

            showPossibleDestinations(possibleDestinations, board, selectedPiece);

            outputView.printBoard(pieces);
            board.changeTurn();
        }
    }

    private void showPossibleDestinations(Set<Position> possibleDestinations, Board board, Piece selectedPiece) {
        if (possibleDestinations.isEmpty()) {
            outputView.printCannotMove();
        }
        if (!possibleDestinations.isEmpty()) {
            outputView.printPossibleRoutes(possibleDestinations);
            UserExceptionHandler.retryUntilSuccess(() -> movePiece(board, selectedPiece, possibleDestinations));
        }
    }
    
    private Piece selectPiece(Board board) {
        Position position = inputView.inputPiecePosition();
        return board.selectPiece(position);
    }

    private void movePiece(Board board, Piece selectedPiece, Set<Position> possibleDestinations) {
        Position destination = inputView.inputDestination();
        board.movePiece(destination, selectedPiece, possibleDestinations);
    }
}
