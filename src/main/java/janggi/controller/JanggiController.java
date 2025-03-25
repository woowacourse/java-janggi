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
        final Board board = generateBoard();
        final List<Piece> pieces = board.getPieces();
        while (true) {
            try {
                startGame(board, pieces);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void startGame(final Board board, final List<Piece> pieces) {
        outputView.printBoard(pieces);
        final Team currentTurn = board.getTurn();
        outputView.printTurn(currentTurn);

        final Position position = inputView.inputPiecePosition();
        final Piece selectPiece = board.selectPiece(position);

        final Set<Route> possibleRoutes = board.findPossibleRoutes(selectPiece);
        outputView.printPossibleRoutes(possibleRoutes);

        final Position destination = inputView.inputDestination();
        if (canMove(possibleRoutes, destination)) {
            board.movePiece(destination, selectPiece);
            board.changeTurn();
            return;
        }
        throw new IllegalArgumentException("해당 위치로 갈 수 없습니다.");
    }

    private boolean canMove(final Set<Route> possibleRoutes, final Position destination) {
        return possibleRoutes.stream()
                .anyMatch(route -> route.isDestination(destination));
    }

    private Board generateBoard() {
        while (true) {
            try {
                final BoardSetup redBoardSetup = inputView.inputBoardSetup(RED);
                final BoardSetup blueBoardSetup = inputView.inputBoardSetup(BLUE);
                return new Board(redBoardSetup, blueBoardSetup);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
